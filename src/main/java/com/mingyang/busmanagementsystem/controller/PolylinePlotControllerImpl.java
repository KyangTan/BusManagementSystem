package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.polylinePlot.GetPolylinePlotResponseDto;
import com.mingyang.busmanagementsystem.model.entity.PolylinePlot;
import com.mingyang.busmanagementsystem.service.polylinePlot.PolylinePlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.CreatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.GetPolylinePlotListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.polylinePlot.UpdatePolylinePlotRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/polyline-plot")
public class PolylinePlotControllerImpl {
    private final PolylinePlotService polylinePlotService;

    @Autowired
    public PolylinePlotControllerImpl(PolylinePlotService polylinePlotService) {
        this.polylinePlotService = polylinePlotService;
    }

    private GetPolylinePlotResponseDto convertToDto(PolylinePlot polylinePlot) {
        if (polylinePlot == null)
            return null;
        GetPolylinePlotResponseDto dto = new GetPolylinePlotResponseDto();
        dto.setId(polylinePlot.getId());
        dto.setPolylineData(polylinePlot.getPolylineData());
        dto.setCreatedAt(polylinePlot.getCreatedAt());
        dto.setUpdatedAt(polylinePlot.getUpdatedAt());
        return dto;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetPolylinePlotResponseDto>> getPolylinePlotById(@PathVariable Long id) {
        PolylinePlot polylinePlot = polylinePlotService.getPolylinePlotById(id);
        return ResponseEntity
                .ok(ApiResponse.success("Polyline plot retrieved successfully", convertToDto(polylinePlot)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetPolylinePlotResponseDto>>> getPolylinePlotList(
            @RequestParam(required = false) String polylineData) {
        GetPolylinePlotListRequestDto requestDto = new GetPolylinePlotListRequestDto();
        requestDto.setPolylineData(polylineData);

        List<PolylinePlot> polylinePlots = polylinePlotService.getPolylinePlotList(requestDto);
        List<GetPolylinePlotResponseDto> response = polylinePlots.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Polyline plots retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetPolylinePlotResponseDto>> createPolylinePlot(
            @RequestBody CreatePolylinePlotRequestDto createPolylinePlotRequestDto) {
        PolylinePlot polylinePlot = polylinePlotService.createPolylinePlot(createPolylinePlotRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Polyline plot created successfully", convertToDto(polylinePlot)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetPolylinePlotResponseDto>> updatePolylinePlot(@PathVariable Long id,
            @RequestBody UpdatePolylinePlotRequestDto updatePolylinePlotRequestDto) {
        PolylinePlot polylinePlot = polylinePlotService.updatePolylinePlot(id, updatePolylinePlotRequestDto);
        return ResponseEntity.ok(ApiResponse.success("Polyline plot updated successfully", convertToDto(polylinePlot)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePolylinePlot(@PathVariable Long id) {
        polylinePlotService.deletePolylinePlot(id);
        return ResponseEntity.ok(ApiResponse.success("Polyline plot deleted successfully", null));
    }
}