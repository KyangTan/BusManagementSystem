package com.mingyang.busmanagementsystem.service.bus;

import com.mingyang.busmanagementsystem.model.entity.Bus;
import com.mingyang.busmanagementsystem.model.dto.request.bus.CreateBusRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.GetBusListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.UpdateBusRequestDto;
import java.util.List;

public interface BusService {
    Bus createBus(CreateBusRequestDto createBusRequestDto);

    Bus getBusById(Long id);

    List<Bus> getAllBuses(GetBusListRequestDto getBusListRequestDto);

    Bus updateBus(Long id, UpdateBusRequestDto updateBusRequestDto);

    void deleteBus(Long id);
}
