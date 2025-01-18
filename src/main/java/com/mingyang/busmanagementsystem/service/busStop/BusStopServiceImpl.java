package com.mingyang.busmanagementsystem.service.busStop;

import com.mingyang.busmanagementsystem.model.dto.request.busStop.GetBusStopListRequestDto;
import com.mingyang.busmanagementsystem.model.entity.BusStop;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.CreateBusStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.UpdateBusStopRequestDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.mingyang.busmanagementsystem.repository.BusStopRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class BusStopServiceImpl implements BusStopService {
    @Autowired
    private BusStopRepository busStopRepository;

    @Override
    public List<BusStop> getBusStopList(GetBusStopListRequestDto getBusStopListRequestDto) {
        List<BusStop> busStops = busStopRepository.findAll();

        if (getBusStopListRequestDto != null) {
            return busStops.stream()
                    .filter(busStop -> (getBusStopListRequestDto.getName() == null ||
                            getBusStopListRequestDto.getName().equals(busStop.getName())) &&
                            (getBusStopListRequestDto.getShortName() == null ||
                                    getBusStopListRequestDto.getShortName().equals(busStop.getShortName()))
                            &&
                            (getBusStopListRequestDto.getAddress() == null ||
                                    getBusStopListRequestDto.getAddress().equals(busStop.getAddress()))
                            &&
                            (getBusStopListRequestDto.getLatitude() == null ||
                                    getBusStopListRequestDto.getLatitude().equals(busStop.getLatitude()))
                            &&
                            (getBusStopListRequestDto.getLongitude() == null ||
                                    getBusStopListRequestDto.getLongitude().equals(busStop.getLongitude()))
                            &&
                            (getBusStopListRequestDto.getIsHidden() == null ||
                                    getBusStopListRequestDto.getIsHidden().equals(busStop.getIsHidden())))
                    .collect(Collectors.toList());
        }

        return busStops;
    }

    @Override
    public BusStop getBusStopById(Long id) {
        return busStopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus stop not found with id: " + id));
    }

    @Override
    public BusStop createBusStop(CreateBusStopRequestDto createBusStopRequestDto) {
        BusStop busStop = new BusStop();
        busStop.setName(createBusStopRequestDto.getName());
        busStop.setShortName(createBusStopRequestDto.getShortName());
        busStop.setAddress(createBusStopRequestDto.getAddress());
        busStop.setLatitude(createBusStopRequestDto.getLatitude());
        busStop.setLongitude(createBusStopRequestDto.getLongitude());
        busStop.setIsHidden(createBusStopRequestDto.getIsHidden());
        busStop.setCreatedAt(LocalDateTime.now());
        busStop.setUpdatedAt(LocalDateTime.now());
        return busStopRepository.save(busStop);
    }

    @Override
    @Transactional
    public BusStop updateBusStop(Long id, UpdateBusStopRequestDto updateBusStopRequestDto) {
        BusStop busStop = busStopRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus stop not found with id: " + id));
        busStop.setName(updateBusStopRequestDto.getName());
        busStop.setShortName(updateBusStopRequestDto.getShortName());
        busStop.setAddress(updateBusStopRequestDto.getAddress());
        busStop.setLatitude(updateBusStopRequestDto.getLatitude());
        busStop.setLongitude(updateBusStopRequestDto.getLongitude());
        busStop.setIsHidden(updateBusStopRequestDto.getIsHidden());
        busStop.setUpdatedAt(LocalDateTime.now());
        return busStopRepository.save(busStop);
    }

    @Override
    @Transactional
    public void deleteBusStop(Long id) {
        BusStop busStop = busStopRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus stop not found with id: " + id));
        busStop.setUpdatedAt(LocalDateTime.now());
        busStopRepository.delete(busStop);
    }

    @Override
    public List<BusStop> searchBusStop(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty");
        }
        return busStopRepository.findByNameContainingIgnoreCase(name.trim());
    }

}
