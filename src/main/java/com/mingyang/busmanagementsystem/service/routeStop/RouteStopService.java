package com.mingyang.busmanagementsystem.service.routeStop;

import com.mingyang.busmanagementsystem.model.dto.request.routeStop.CreateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.GetRouteStopListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.UpdateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.model.entity.RouteStop;

import java.util.List;

public interface RouteStopService {
    List<RouteStop> getRouteStopList(GetRouteStopListRequestDto requestDto);

    RouteStop getRouteStopById(Long id);

    RouteStop createRouteStop(CreateRouteStopRequestDto requestDto);

    RouteStop updateRouteStop(Long id, UpdateRouteStopRequestDto requestDto);

    void deleteRouteStop(Long id);

}
