package com.mingyang.busmanagementsystem.service.tripRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.CreateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.GetTripRecordListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.UpdateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.entity.TripRecord;
import com.mingyang.busmanagementsystem.repository.TripRecordRepository;
import com.mingyang.busmanagementsystem.service.busRoute.BusRouteService;
import com.mingyang.busmanagementsystem.service.bus.BusService;
import com.mingyang.busmanagementsystem.model.entity.BusRoute;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TripRecordServiceImpl implements TripRecordService {

    private final TripRecordRepository tripRecordRepository;
    private final BusRouteService busRouteService;
    private final BusService busService;

    @Autowired
    public TripRecordServiceImpl(
            TripRecordRepository tripRecordRepository,
            BusRouteService busRouteService,
            BusService busService) {
        this.tripRecordRepository = tripRecordRepository;
        this.busRouteService = busRouteService;
        this.busService = busService;
    }

    @Override
    public List<TripRecord> getTripRecordList(GetTripRecordListRequestDto requestDto) {
        if (requestDto == null) {
            return tripRecordRepository.findAll();
        }

        return tripRecordRepository.findByFilters(
                requestDto.getBusRouteId(),
                requestDto.getBusId(),
                requestDto.getTripStatus(),
                requestDto.getStartTime(),
                requestDto.getEndTime());
    }

    @Override
    public TripRecord getTripRecordById(Long id) {
        return tripRecordRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip record not found with id: " + id));
    }

    @Override
    public TripRecord createTripRecord(CreateTripRecordRequestDto requestDto) {
        validateTripRecordCreation(requestDto);

        TripRecord tripRecord = new TripRecord();
        tripRecord.setBusRoute(busRouteService.getBusRouteById(requestDto.getBusRouteId()));
        tripRecord.setBus(busService.getBusById(requestDto.getBusId()));
        tripRecord.setCurrentRouteStopSequence(requestDto.getCurrentRouteStopSequence());
        tripRecord.setTripStatus(requestDto.getTripStatus());
        tripRecord.setStartTime(requestDto.getStartTime());
        tripRecord.setEndTime(requestDto.getEndTime());
        tripRecord.setCreatedAt(LocalDateTime.now());
        tripRecord.setUpdatedAt(LocalDateTime.now());
        return tripRecordRepository.save(tripRecord);
    }

    private void validateTripRecordCreation(CreateTripRecordRequestDto requestDto) {
        // Check if there's another trip record with the same bus, but end time is null
        TripRecord tripRecordCheck = tripRecordRepository.findByBusIdAndEndTimeIsNull(requestDto.getBusId());
        if (tripRecordCheck != null) {
            throw new EntityExistsException("There's already an active trip record with the same bus");
        }

        // Check if tripStatus is valid
        if (!Arrays.asList("ON_THE_WAY", "ARRIVING", "COMPLETED").contains(requestDto.getTripStatus())) {
            throw new IllegalArgumentException("Invalid trip status");
        }

        // Check if Route Stop Sequence is valid
        BusRoute busRoute = busRouteService.getBusRouteById(requestDto.getBusRouteId());
        busRoute.getRouteStops().stream()
                .filter(rs -> rs.getSequence() == requestDto.getCurrentRouteStopSequence())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Route stop not found with sequence: " + requestDto.getCurrentRouteStopSequence()));

        // Check if start time is valid
        if (requestDto.getStartTime() == null) {
            throw new IllegalArgumentException("Start time is required");
        }
    }

    @Override
    @Transactional
    public TripRecord updateTripRecord(Long id, UpdateTripRecordRequestDto requestDto) {
        TripRecord tripRecord = tripRecordRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip record not found with id: " + id));

        validateTripRecordUpdate(tripRecord.getBusRoute().getId(), requestDto);

        if (requestDto.getCurrentRouteStopSequence() != null) {
            tripRecord.setCurrentRouteStopSequence(requestDto.getCurrentRouteStopSequence());
        }
        if (requestDto.getTripStatus() != null) {
            tripRecord.setTripStatus(requestDto.getTripStatus());
        }
        if (requestDto.getEndTime() != null) {
            tripRecord.setEndTime(requestDto.getEndTime());
        }

        tripRecord.setUpdatedAt(LocalDateTime.now());
        return tripRecordRepository.save(tripRecord);
    }

    private void validateTripRecordUpdate(Long busRouteId, UpdateTripRecordRequestDto requestDto) {
        // Check if Route Stop Sequence is valid
        BusRoute busRoute = busRouteService.getBusRouteById(busRouteId);
        busRoute.getRouteStops().stream()
                .filter(rs -> rs.getSequence() == requestDto.getCurrentRouteStopSequence())
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Route stop not found with sequence: " + requestDto.getCurrentRouteStopSequence()));

        // Check if tripStatus is valid
        if (!Arrays.asList("ON_THE_WAY", "ARRIVING", "COMPLETED").contains(requestDto.getTripStatus())) {
            throw new IllegalArgumentException("Invalid trip status");
        }
    }

    @Override
    @Transactional
    public void deleteTripRecord(Long id) {
        TripRecord tripRecord = tripRecordRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Trip record not found with id: " + id));
        tripRecordRepository.delete(tripRecord);
    }
}