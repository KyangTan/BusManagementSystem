package com.mingyang.busmanagementsystem.service.routeStop;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.mingyang.busmanagementsystem.repository.RouteStopRepository;
import com.mingyang.busmanagementsystem.model.entity.RouteStop;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.GetRouteStopListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.CreateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.UpdateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.service.busRoute.BusRouteService;
import com.mingyang.busmanagementsystem.service.busStop.BusStopService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RouteStopServiceImpl implements RouteStopService {

    private final RouteStopRepository routeStopRepository;
    private final BusRouteService busRouteService;
    private final BusStopService busStopService;

    @Autowired
    public RouteStopServiceImpl(
            RouteStopRepository routeStopRepository,
            BusRouteService busRouteService,
            BusStopService busStopService) {
        this.routeStopRepository = routeStopRepository;
        this.busRouteService = busRouteService;
        this.busStopService = busStopService;
    }

    @Override
    public List<RouteStop> getRouteStopList(GetRouteStopListRequestDto requestDto) {
        if (requestDto.getRouteId() == null) {
            return routeStopRepository.findAll();
        }
        return routeStopRepository.findByBusRouteId(requestDto.getRouteId());
    }

    @Override
    public RouteStop getRouteStopById(Long id) {
        return routeStopRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route stop not found with id: " + id));
    }

    @Override
    public RouteStop createRouteStop(CreateRouteStopRequestDto requestDto) {
        RouteStop routeStop = new RouteStop();
        routeStop.setBusRoute(busRouteService.getBusRouteById(requestDto.getRouteId()));
        routeStop.setBusStop(busStopService.getBusStopById(requestDto.getStopId()));
        routeStop.setSequence(requestDto.getSequence());
        routeStop.setDuration(requestDto.getDuration());
        routeStop.setCreatedAt(LocalDateTime.now());
        routeStop.setUpdatedAt(LocalDateTime.now());
        return routeStopRepository.save(routeStop);
    }

    @Override
    @Transactional
    public RouteStop updateRouteStop(Long id, UpdateRouteStopRequestDto requestDto) {
        RouteStop routeStop = routeStopRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Route stop not found with id: " + id));
        routeStop.setSequence(requestDto.getSequence());
        routeStop.setDuration(requestDto.getDuration());
        routeStop.setUpdatedAt(LocalDateTime.now());
        return routeStopRepository.save(routeStop);
    }

    @Override
    @Transactional
    public void deleteRouteStop(Long id) {
        RouteStop routeStop = routeStopRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Route stop not found with id: " + id));
        routeStop.setUpdatedAt(LocalDateTime.now());
        routeStopRepository.save(routeStop);
        routeStopRepository.delete(routeStop);
    }
}
