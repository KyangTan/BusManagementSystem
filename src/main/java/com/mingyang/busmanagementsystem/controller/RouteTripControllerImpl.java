package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.routeTrip.GetRouteTripResponseDto;
import com.mingyang.busmanagementsystem.model.entity.RouteTrip;
import com.mingyang.busmanagementsystem.service.routeTrip.RouteTripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.CreateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.GetRouteTripListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeTrip.UpdateRouteTripRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/route-trip")
public class RouteTripControllerImpl {
    private final RouteTripService routeTripService;

    @Autowired
    public RouteTripControllerImpl(RouteTripService routeTripService) {
        this.routeTripService = routeTripService;
    }

    private GetRouteTripResponseDto convertToDto(RouteTrip routeTrip) {
        if (routeTrip == null)
            return null;
        GetRouteTripResponseDto dto = new GetRouteTripResponseDto();
        dto.setId(routeTrip.getId());
        dto.setStartTime(routeTrip.getStartTime());
        dto.setEndTime(routeTrip.getEndTime());
        dto.setIsActive(routeTrip.getIsActive());

        dto.setBusRouteId(routeTrip.getBusRoute().getId());
        dto.setStartRouteStopId(routeTrip.getStartRouteStop().getId());

        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetRouteTripResponseDto>> getRouteTripById(@PathVariable Long id) {
        RouteTrip routeTrip = routeTripService.getRouteTripById(id);
        return ResponseEntity
                .ok(ApiResponse.success("Route trip retrieved successfully", convertToDto(routeTrip)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetRouteTripResponseDto>>> getRouteTripList(
            @RequestParam(required = false) Long busRouteId,
            @RequestParam(required = false) Boolean isActive) {
        GetRouteTripListRequestDto requestDto = new GetRouteTripListRequestDto();
        requestDto.setBusRouteId(busRouteId);
        requestDto.setIsActive(isActive);

        List<RouteTrip> routeTrips = routeTripService.getRouteTripList(requestDto);
        List<GetRouteTripResponseDto> response = routeTrips.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Route trips retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetRouteTripResponseDto>> createRouteTrip(
            @RequestBody CreateRouteTripRequestDto createRouteTripRequestDto) {
        RouteTrip routeTrip = routeTripService.createRouteTrip(createRouteTripRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Route trip created successfully", convertToDto(routeTrip)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetRouteTripResponseDto>> updateRouteTrip(@PathVariable Long id,
            @RequestBody UpdateRouteTripRequestDto updateRouteTripRequestDto) {
        RouteTrip routeTrip = routeTripService.updateRouteTrip(id, updateRouteTripRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Route trip updated successfully", convertToDto(routeTrip)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRouteTrip(@PathVariable Long id) {
        routeTripService.deleteRouteTrip(id);
        return ResponseEntity.ok(ApiResponse.success("Route trip deleted successfully", null));
    }
}