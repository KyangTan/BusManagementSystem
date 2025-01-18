package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.routeStop.GetRouteStopResponseDto;
import com.mingyang.busmanagementsystem.model.entity.RouteStop;
import com.mingyang.busmanagementsystem.service.routeStop.RouteStopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.routeStop.CreateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.GetRouteStopListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.routeStop.UpdateRouteStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;
import com.mingyang.busmanagementsystem.model.dto.response.busStop.GetBusStopResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/route-stop")
public class RouteStopControllerImpl {
    @Autowired
    private RouteStopService routeStopService;

    private GetRouteStopResponseDto convertToDto(RouteStop routeStop) {
        if (routeStop == null)
            return null;
        GetRouteStopResponseDto dto = new GetRouteStopResponseDto();
        dto.setId(routeStop.getId());
        dto.setSequence(routeStop.getSequence());
        dto.setDuration(routeStop.getDuration());
        
        GetBusStopResponseDto busStopDto = new GetBusStopResponseDto();
        busStopDto.setId(routeStop.getBusStop().getId());
        busStopDto.setName(routeStop.getBusStop().getName());
        busStopDto.setShortName(routeStop.getBusStop().getShortName());
        busStopDto.setAddress(routeStop.getBusStop().getAddress());
        busStopDto.setLatitude(routeStop.getBusStop().getLatitude());
        busStopDto.setLongitude(routeStop.getBusStop().getLongitude());
        busStopDto.setCreatedAt(routeStop.getBusStop().getCreatedAt());
        busStopDto.setUpdatedAt(routeStop.getBusStop().getUpdatedAt());
        busStopDto.setIsHidden(routeStop.getBusStop().getIsHidden());
        dto.setBusStop(busStopDto);

        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetRouteStopResponseDto>> getRouteStopById(@PathVariable Long id) {
        RouteStop routeStop = routeStopService.getRouteStopById(id);
        return ResponseEntity.ok(ApiResponse.success("Route stop retrieved successfully", convertToDto(routeStop)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetRouteStopResponseDto>>> getRouteStopList(
            @RequestParam(required = false) Long routeId) {
        GetRouteStopListRequestDto requestDto = new GetRouteStopListRequestDto();
        requestDto.setRouteId(routeId);

        List<RouteStop> routeStops = routeStopService.getRouteStopList(requestDto);
        List<GetRouteStopResponseDto> response = routeStops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Route stops retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetRouteStopResponseDto>> createRouteStop(
            @RequestBody CreateRouteStopRequestDto createRouteStopRequestDto) {
        RouteStop routeStop = routeStopService.createRouteStop(createRouteStopRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Route stop created successfully", convertToDto(routeStop)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetRouteStopResponseDto>> updateRouteStop(@PathVariable Long id,
            @RequestBody UpdateRouteStopRequestDto updateRouteStopRequestDto) {
        RouteStop routeStop = routeStopService.updateRouteStop(id, updateRouteStopRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Route stop updated successfully", convertToDto(routeStop)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRouteStop(@PathVariable Long id) {
        routeStopService.deleteRouteStop(id);
        return ResponseEntity.ok(ApiResponse.success("Route stop deleted successfully", null));
    }
}
