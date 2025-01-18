package com.mingyang.busmanagementsystem.service.routeTrip;

import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.CreateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.GetRouteTripListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.UpdateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.entity.RouteTrip;
import java.util.List;

public interface RouteTripService {
    List<RouteTrip> getRouteTripList(GetRouteTripListRequestDto requestDto);

    RouteTrip getRouteTripById(Long id);

    RouteTrip createRouteTrip(CreateRouteTripRequestDto requestDto);

    RouteTrip updateRouteTrip(Long id, UpdateRouteTripRequestDto requestDto);

    void deleteRouteTrip(Long id);
} 