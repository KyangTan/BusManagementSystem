package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.busStop.GetBusStopResponseDto;
import com.mingyang.busmanagementsystem.model.entity.BusStop;
import com.mingyang.busmanagementsystem.service.busStop.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.busStop.CreateBusStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.GetBusStopListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.busStop.UpdateBusStopRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bus-stop")
public class BusStopControllerImpl {
    @Autowired
    private BusStopService busStopService;

    private GetBusStopResponseDto convertToDto(BusStop busStop) {
        if (busStop == null)
            return null;
        GetBusStopResponseDto dto = new GetBusStopResponseDto();
        dto.setId(busStop.getId());
        dto.setName(busStop.getName());
        dto.setShortName(busStop.getShortName());
        dto.setAddress(busStop.getAddress());
        dto.setLatitude(busStop.getLatitude());
        dto.setLongitude(busStop.getLongitude());
        dto.setIsHidden(busStop.getIsHidden());
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusStopResponseDto>> getBusStopById(@PathVariable Long id) {
        BusStop busStop = busStopService.getBusStopById(id);
        return ResponseEntity.ok(ApiResponse.success("Bus stop retrieved successfully", convertToDto(busStop)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBusStopResponseDto>>> getBusStopList(
            @RequestBody(required = false) GetBusStopListRequestDto getBusStopListRequestDto) {
        List<BusStop> busStops = busStopService.getBusStopList(getBusStopListRequestDto);
        List<GetBusStopResponseDto> response = busStops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Bus stops retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetBusStopResponseDto>> createBusStop(
            @RequestBody CreateBusStopRequestDto createBusStopRequestDto) {
        BusStop busStop = busStopService.createBusStop(createBusStopRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus stop created successfully", convertToDto(busStop)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusStopResponseDto>> updateBusStop(@PathVariable Long id,
            @RequestBody UpdateBusStopRequestDto updateBusStopRequestDto) {
        BusStop busStop = busStopService.updateBusStop(id, updateBusStopRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus stop updated successfully", convertToDto(busStop)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBusStop(@PathVariable Long id) {
        busStopService.deleteBusStop(id);
        return ResponseEntity.ok(ApiResponse.success("Bus stop deleted successfully", null));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<GetBusStopResponseDto>>> searchBusStopByName(
            @RequestParam String name) {
        List<BusStop> busStops = busStopService.searchBusStop(name);
        List<GetBusStopResponseDto> response = busStops.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Bus stops retrieved successfully", response));
    }
}
