package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.busRoute.GetBusRouteResponseDto;
import com.mingyang.busmanagementsystem.model.entity.BusRoute;
import com.mingyang.busmanagementsystem.service.busRoute.BusRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.busRoute.CreateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.GetBusRouteListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busRoute.UpdateBusRouteRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bus-route")
public class BusRouteControllerImpl {
    private final BusRouteService busRouteService;

    @Autowired
    public BusRouteControllerImpl(BusRouteService busRouteService) {
        this.busRouteService = busRouteService;
    }

    private GetBusRouteResponseDto convertToDto(BusRoute busRoute) {
        if (busRoute == null)
            return null;
        GetBusRouteResponseDto dto = new GetBusRouteResponseDto();
        dto.setId(busRoute.getId());
        dto.setName(busRoute.getName());
        dto.setDescription(busRoute.getDescription());
        dto.setDistance(busRoute.getDistance());
        dto.setMapColor(busRoute.getMapColor());
        dto.setIsActive(busRoute.getIsActive());

        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusRouteResponseDto>> getBusRouteById(@PathVariable Long id) {
        BusRoute busRoute = busRouteService.getBusRouteById(id);
        return ResponseEntity.ok(ApiResponse.success("Bus route retrieved successfully", convertToDto(busRoute)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBusRouteResponseDto>>> getBusRouteList(
            @RequestBody(required = false) GetBusRouteListRequestDto getBusRouteListRequestDto) {
        List<BusRoute> busRoutes = busRouteService.getBusRouteList(getBusRouteListRequestDto);
        List<GetBusRouteResponseDto> response = busRoutes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Bus routes retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetBusRouteResponseDto>> createBusRoute(
            @RequestBody CreateBusRouteRequestDto createBusRouteRequestDto) {
        BusRoute busRoute = busRouteService.createBusRoute(createBusRouteRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus route created successfully", convertToDto(busRoute)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusRouteResponseDto>> updateBusRoute(@PathVariable Long id,
            @RequestBody UpdateBusRouteRequestDto updateBusRouteRequestDto) {
        BusRoute busRoute = busRouteService.updateBusRoute(id, updateBusRouteRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus route updated successfully", convertToDto(busRoute)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBusRoute(@PathVariable Long id) {
        busRouteService.deleteBusRoute(id);
        return ResponseEntity.ok(ApiResponse.success("Bus route deleted successfully", null));
    }
}
