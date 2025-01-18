package com.mingyang.busmanagementsystem.service.bus;

import com.mingyang.busmanagementsystem.model.dto.request.bus.CreateBusRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.GetBusListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.UpdateBusRequestDto;
import com.mingyang.busmanagementsystem.model.entity.Bus;
import com.mingyang.busmanagementsystem.repository.BusRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus createBus(CreateBusRequestDto createBusRequestDto) {
        Bus bus = new Bus();
        bus.setPlateNo(createBusRequestDto.getPlateNo());
        bus.setCarStatus(createBusRequestDto.getCarStatus());
        bus.setCarType(createBusRequestDto.getCarType());
        bus.setInternalBus(createBusRequestDto.getInternalBus());
        LocalDateTime now = LocalDateTime.now();
        bus.setCreatedAt(now);
        bus.setUpdatedAt(now);
        return busRepository.save(bus);
    }

    @Override
    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .map(bus -> !bus.getIsDeleted() ? bus : null)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with id: " + id));
    }

    @Override
    public List<Bus> getAllBuses(GetBusListRequestDto getBusListRequestDto) {
        List<Bus> buses = busRepository.findAll().stream()
                .filter(bus -> !bus.getIsDeleted())
                .collect(Collectors.toList());

        if (getBusListRequestDto != null) {
            return buses.stream()
                    .filter(bus -> (getBusListRequestDto.getPlateNo() == null ||
                            getBusListRequestDto.getPlateNo().equals(bus.getPlateNo())) &&
                            (getBusListRequestDto.getCarStatus() == null ||
                                    getBusListRequestDto.getCarStatus().equals(bus.getCarStatus()))
                            &&
                            (getBusListRequestDto.getCarType() == null ||
                                    getBusListRequestDto.getCarType().equals(bus.getCarType()))
                            &&
                            (getBusListRequestDto.getInternalBus() == null ||
                                    getBusListRequestDto.getInternalBus().equals(bus.getInternalBus())))
                    .collect(Collectors.toList());
        }

        return buses;
    }

    @Override
    @Transactional
    public Bus updateBus(Long id, UpdateBusRequestDto updateBusRequestDto) {
        Bus bus = busRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with id: " + id));
        if (bus != null && !bus.getIsDeleted()) {
            bus.setPlateNo(updateBusRequestDto.getPlateNo());
            bus.setCarStatus(updateBusRequestDto.getCarStatus());
            bus.setCarType(updateBusRequestDto.getCarType());
            bus.setInternalBus(updateBusRequestDto.getInternalBus());
            bus.setUpdatedAt(LocalDateTime.now());
            return busRepository.save(bus);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteBus(Long id) {
        Bus bus = busRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus not found with id: " + id));
        if (bus != null && !bus.getIsDeleted()) {
            bus.setIsDeleted(true);
            bus.setUpdatedAt(LocalDateTime.now());
            busRepository.save(bus);
        }
    }
}
