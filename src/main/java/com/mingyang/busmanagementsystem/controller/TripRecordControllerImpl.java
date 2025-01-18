package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.tripRecord.GetTripRecordResponseDto;
import com.mingyang.busmanagementsystem.model.entity.TripRecord;
import com.mingyang.busmanagementsystem.service.tripRecord.TripRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.CreateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.GetTripRecordListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.tripRecord.UpdateTripRecordRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;
import com.mingyang.busmanagementsystem.model.dto.response.busRoute.GetBusRouteResponseDto;
import com.mingyang.busmanagementsystem.model.dto.response.bus.GetBusResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trip-record")
public class TripRecordControllerImpl {
    private final TripRecordService tripRecordService;

    @Autowired
    public TripRecordControllerImpl(TripRecordService tripRecordService) {
        this.tripRecordService = tripRecordService;
    }

    private GetTripRecordResponseDto convertToDto(TripRecord tripRecord) {
        if (tripRecord == null)
            return null;
        GetTripRecordResponseDto dto = new GetTripRecordResponseDto();
        dto.setId(tripRecord.getId());
        dto.setCurrentRouteStopSequence(tripRecord.getCurrentRouteStopSequence());
        dto.setTripStatus(tripRecord.getTripStatus());
        dto.setStartTime(tripRecord.getStartTime());
        dto.setEndTime(tripRecord.getEndTime());
        dto.setCreatedAt(tripRecord.getCreatedAt());
        dto.setUpdatedAt(tripRecord.getUpdatedAt());

        // Convert BusRoute
        GetBusRouteResponseDto busRouteDto = new GetBusRouteResponseDto();
        busRouteDto.setId(tripRecord.getBusRoute().getId());
        busRouteDto.setName(tripRecord.getBusRoute().getName());
        busRouteDto.setDescription(tripRecord.getBusRoute().getDescription());
        busRouteDto.setDistance(tripRecord.getBusRoute().getDistance());
        busRouteDto.setMapColor(tripRecord.getBusRoute().getMapColor());
        busRouteDto.setIsActive(tripRecord.getBusRoute().getIsActive());
        dto.setBusRoute(busRouteDto);

        // Convert Bus
        GetBusResponseDto busDto = new GetBusResponseDto();
        busDto.setId(tripRecord.getBus().getId());
        busDto.setPlateNo(tripRecord.getBus().getPlateNo());
        busDto.setCarStatus(tripRecord.getBus().getCarStatus());
        busDto.setCarType(tripRecord.getBus().getCarType());
        busDto.setInternalBus(tripRecord.getBus().getInternalBus());
        dto.setBus(busDto);

        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetTripRecordResponseDto>> getTripRecordById(@PathVariable Long id) {
        TripRecord tripRecord = tripRecordService.getTripRecordById(id);
        return ResponseEntity
                .ok(ApiResponse.success("Trip record retrieved successfully", convertToDto(tripRecord)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetTripRecordResponseDto>>> getTripRecordList(
            @RequestParam(required = false) Long busRouteId,
            @RequestParam(required = false) Long busId,
            @RequestParam(required = false) String tripStatus,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        GetTripRecordListRequestDto requestDto = new GetTripRecordListRequestDto();
        requestDto.setBusRouteId(busRouteId);
        requestDto.setBusId(busId);
        requestDto.setTripStatus(tripStatus);
        requestDto.setStartTime(startTime);
        requestDto.setEndTime(endTime);

        List<TripRecord> tripRecords = tripRecordService.getTripRecordList(requestDto);
        List<GetTripRecordResponseDto> response = tripRecords.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Trip records retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetTripRecordResponseDto>> createTripRecord(
            @RequestBody CreateTripRecordRequestDto createTripRecordRequestDto) {
        TripRecord tripRecord = tripRecordService.createTripRecord(createTripRecordRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Trip record created successfully", convertToDto(tripRecord)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetTripRecordResponseDto>> updateTripRecord(@PathVariable Long id,
            @RequestBody UpdateTripRecordRequestDto updateTripRecordRequestDto) {
        TripRecord tripRecord = tripRecordService.updateTripRecord(id, updateTripRecordRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Trip record updated successfully", convertToDto(tripRecord)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTripRecord(@PathVariable Long id) {
        tripRecordService.deleteTripRecord(id);
        return ResponseEntity.ok(ApiResponse.success("Trip record deleted successfully", null));
    }
}