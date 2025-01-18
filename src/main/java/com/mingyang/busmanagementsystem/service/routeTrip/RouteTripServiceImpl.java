package com.mingyang.busmanagementsystem.service.routeTrip;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.CreateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.GetRouteTripListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.UpdateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.entity.RouteTrip;
import com.mingyang.busmanagementsystem.repository.RouteTripRepository;
import com.mingyang.busmanagementsystem.service.busRoute.BusRouteService;
import com.mingyang.busmanagementsystem.service.routeStop.RouteStopService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RouteTripServiceImpl implements RouteTripService {

    private final RouteTripRepository routeTripRepository;
    private final BusRouteService busRouteService;
    private final RouteStopService routeStopService;

    @Autowired
    public RouteTripServiceImpl(
            RouteTripRepository routeTripRepository,
            BusRouteService busRouteService,
            RouteStopService routeStopService) {
        this.routeTripRepository = routeTripRepository;
        this.busRouteService = busRouteService;
        this.routeStopService = routeStopService;
    }

    @Override
    public List<RouteTrip> getRouteTripList(GetRouteTripListRequestDto requestDto) {
        List<RouteTrip> routeTrips = routeTripRepository.findAll();

        if (requestDto != null) {
            return routeTrips.stream()
                    .filter(routeTrip -> 
                        (requestDto.getBusRouteId() == null ||
                            requestDto.getBusRouteId().equals(routeTrip.getBusRoute().getId())) &&
                        (requestDto.getStartTime() == null ||
                            requestDto.getStartTime().equals(routeTrip.getStartTime())) &&
                        (requestDto.getEndTime() == null ||
                            requestDto.getEndTime().equals(routeTrip.getEndTime())) &&
                        (requestDto.getIsActive() == null ||
                            requestDto.getIsActive().equals(routeTrip.getIsActive())) &&
                        (requestDto.getStartRouteStopId() == null ||
                            requestDto.getStartRouteStopId().equals(routeTrip.getStartRouteStop().getId())))
                    .collect(Collectors.toList());
        }

        return routeTrips;
    }

    @Override
    public RouteTrip getRouteTripById(Long id) {
        return routeTripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route trip not found with id: " + id));
    }

    @Override
    public RouteTrip createRouteTrip(CreateRouteTripRequestDto requestDto) {
        RouteTrip routeTrip = new RouteTrip();
        routeTrip.setBusRoute(busRouteService.getBusRouteById(requestDto.getBusRouteId()));
        routeTrip.setStartRouteStop(routeStopService.getRouteStopById(requestDto.getStartRouteStopId()));
        routeTrip.setStartTime(requestDto.getStartTime());
        routeTrip.setEndTime(requestDto.getEndTime());
        routeTrip.setIsActive(requestDto.getIsActive());
        return routeTripRepository.save(routeTrip);
    }

    @Override
    @Transactional
    public RouteTrip updateRouteTrip(Long id, UpdateRouteTripRequestDto requestDto) {
        RouteTrip routeTrip = routeTripRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Route trip not found with id: " + id));
        
        if (requestDto.getStartRouteStopId() != null) {
            routeTrip.setStartRouteStop(routeStopService.getRouteStopById(requestDto.getStartRouteStopId()));
        }
        if (requestDto.getStartTime() != null) {
            routeTrip.setStartTime(requestDto.getStartTime());
        }
        if (requestDto.getEndTime() != null) {
            routeTrip.setEndTime(requestDto.getEndTime());
        }
        if (requestDto.getIsActive() != null) {
            routeTrip.setIsActive(requestDto.getIsActive());
        }
        
        return routeTripRepository.save(routeTrip);
    }

    @Override
    @Transactional
    public void deleteRouteTrip(Long id) {
        RouteTrip routeTrip = routeTripRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route trip not found with id: " + id));
        routeTripRepository.delete(routeTrip);
    }
} 