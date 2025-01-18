package com.mingyang.busmanagementsystem.service.busRoute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.busRoute.CreateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.GetBusRouteListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.UpdateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.entity.BusRoute;
import com.mingyang.busmanagementsystem.repository.BusRouteRepository;
import com.mingyang.busmanagementsystem.repository.RouteStopRepository;
import com.mingyang.busmanagementsystem.repository.PolylinePlotRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class BusRouteServiceImpl implements BusRouteService {

    private final BusRouteRepository busRouteRepository;
    private final RouteStopRepository routeStopRepository;
    private final PolylinePlotRepository polylinePlotRepository;

    @Autowired
    public BusRouteServiceImpl(
            BusRouteRepository busRouteRepository,
            RouteStopRepository routeStopRepository,
            PolylinePlotRepository polylinePlotRepository) {
        this.busRouteRepository = busRouteRepository;
        this.routeStopRepository = routeStopRepository;
        this.polylinePlotRepository = polylinePlotRepository;
    }

    @Override
    public BusRoute getBusRouteById(Long id) {
        return busRouteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus route not found with id: " + id));
    }

    @Override
    public List<BusRoute> getBusRouteList(GetBusRouteListRequestDto requestDto) {
        List<BusRoute> busRoutes = busRouteRepository.findAll();

        if (requestDto != null) {
            return busRoutes.stream()
                    .filter(busRoute -> (requestDto.getName() == null ||
                            requestDto.getName().equals(busRoute.getName())) &&
                            (requestDto.getDescription() == null ||
                                    requestDto.getDescription().equals(busRoute.getDescription()))
                            &&
                            (requestDto.getDistance() == null ||
                                    requestDto.getDistance().equals(busRoute.getDistance()))
                            &&
                            (requestDto.getMapColor() == null ||
                                    requestDto.getMapColor().equals(busRoute.getMapColor()))
                            &&
                            (requestDto.getIsActive() == null ||
                                    requestDto.getIsActive().equals(busRoute.getIsActive()))
                            &&
                            (requestDto.getIsDeleted() == null ||
                                    requestDto.getIsDeleted().equals(busRoute.getIsDeleted())))
                    .collect(Collectors.toList());
        }

        return busRoutes;
    }

    @Override
    public BusRoute createBusRoute(CreateBusRouteRequestDto requestDto) {
        BusRoute busRoute = new BusRoute();
        busRoute.setName(requestDto.getName());
        busRoute.setDescription(requestDto.getDescription());
        busRoute.setDistance(requestDto.getDistance());
        busRoute.setMapColor(requestDto.getMapColor());
        busRoute.setIsActive(requestDto.getIsActive());
        busRoute.setIsDeleted(requestDto.getIsDeleted());
        busRoute.setCreatedAt(LocalDateTime.now());
        busRoute.setUpdatedAt(LocalDateTime.now());
        return busRouteRepository.save(busRoute);
    }

    @Override
    @Transactional
    public BusRoute updateBusRoute(Long id, UpdateBusRouteRequestDto requestDto) {
        BusRoute busRoute = busRouteRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus route not found with id: " + id));
        busRoute.setName(requestDto.getName());
        busRoute.setDescription(requestDto.getDescription());
        busRoute.setDistance(requestDto.getDistance());
        busRoute.setMapColor(requestDto.getMapColor());
        busRoute.setIsActive(requestDto.getIsActive());
        busRoute.setIsDeleted(requestDto.getIsDeleted());
        busRoute.setUpdatedAt(LocalDateTime.now());
        return busRouteRepository.save(busRoute);
    }

    @Override
    @Transactional
    public void deleteBusRoute(Long id) {
        BusRoute busRoute = busRouteRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("Bus route not found with id: " + id));

        if (busRoute.getPolylinePlot() != null || busRoute.getRouteStops() != null) {
            // TODO: delete routeStop and polylinePlot
            routeStopRepository.deleteAll(busRoute.getRouteStops());
            polylinePlotRepository.delete(busRoute.getPolylinePlot());
        }

        busRoute.setIsDeleted(true);
        busRoute.setUpdatedAt(LocalDateTime.now());
        busRouteRepository.save(busRoute);
    }

}
