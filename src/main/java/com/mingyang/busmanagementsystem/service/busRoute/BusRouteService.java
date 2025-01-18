package com.mingyang.busmanagementsystem.service.busRoute;

import java.util.List;

import com.mingyang.busmanagementsystem.model.dto.request.busRoute.CreateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.GetBusRouteListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.UpdateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.entity.BusRoute;

public interface BusRouteService {

    BusRoute getBusRouteById(Long id);

    List<BusRoute> getBusRouteList(GetBusRouteListRequestDto requestDto);

    BusRoute createBusRoute(CreateBusRouteRequestDto requestDto);

    BusRoute updateBusRoute(Long id, UpdateBusRouteRequestDto requestDto);

    void deleteBusRoute(Long id);

}
