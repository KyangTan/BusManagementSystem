package com.mingyang.busmanagementsystem.service.busStop;

import com.mingyang.busmanagementsystem.model.dto.request.busStop.GetBusStopListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.busStop.GetBusStopResponseDto;
import com.mingyang.busmanagementsystem.model.entity.BusStop;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.CreateBusStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.UpdateBusStopRequestDto;
import java.util.List;
public interface BusStopService {
    List<BusStop> getBusStopList(GetBusStopListRequestDto getBusStopListRequestDto);

    BusStop getBusStopById(Long id);

    BusStop createBusStop(CreateBusStopRequestDto createBusStopRequestDto);

    BusStop updateBusStop(Long id, UpdateBusStopRequestDto updateBusStopRequestDto);

    void deleteBusStop(Long id);

    List<BusStop> searchBusStop(String name);
}
