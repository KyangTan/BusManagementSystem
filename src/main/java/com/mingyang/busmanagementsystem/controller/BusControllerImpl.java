package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.bus.GetBusResponseDto;
import com.mingyang.busmanagementsystem.model.entity.Bus;
import com.mingyang.busmanagementsystem.service.bus.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mingyang.busmanagementsystem.model.dto.request.bus.CreateBusRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.UpdateBusRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.bus.GetBusListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bus")
public class BusControllerImpl {
    @Autowired
    private BusService busService;

    private GetBusResponseDto convertToDto(Bus bus) {
        if (bus == null)
            return null;
        GetBusResponseDto dto = new GetBusResponseDto();
        dto.setId(bus.getId());
        dto.setPlateNo(bus.getPlateNo());
        dto.setCarStatus(bus.getCarStatus());
        dto.setCarType(bus.getCarType());
        dto.setInternalBus(bus.getInternalBus());
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusResponseDto>> getBusById(@PathVariable Long id) {
        Bus bus = busService.getBusById(id);
        return ResponseEntity.ok(ApiResponse.success("Bus retrieved successfully", convertToDto(bus)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetBusResponseDto>>> getAllBuses(
            @RequestBody(required = false) GetBusListRequestDto getBusListRequestDto) {
        List<Bus> buses = busService.getAllBuses(getBusListRequestDto);
        List<GetBusResponseDto> response = buses.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Buses retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetBusResponseDto>> createBus(
            @RequestBody CreateBusRequestDto createBusRequestDto) {
        Bus bus = busService.createBus(createBusRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus created successfully", convertToDto(bus)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetBusResponseDto>> updateBus(@PathVariable Long id,
            @RequestBody UpdateBusRequestDto updateBusRequestDto) {
        Bus bus = busService.updateBus(id, updateBusRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Bus updated successfully", convertToDto(bus)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBus(@PathVariable Long id) {
        busService.deleteBus(id);
        return ResponseEntity.ok(ApiResponse.success("Bus deleted successfully", null));
    }
}
