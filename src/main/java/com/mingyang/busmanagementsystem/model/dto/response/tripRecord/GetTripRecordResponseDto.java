package com.mingyang.busmanagementsystem.model.dto.response.tripRecord;

import java.time.LocalDateTime;
import com.mingyang.busmanagementsystem.model.dto.response.busRoute.GetBusRouteResponseDto;
import com.mingyang.busmanagementsystem.model.dto.response.bus.GetBusResponseDto;

public class GetTripRecordResponseDto {
    private Long id;
    private GetBusRouteResponseDto busRoute;
    private GetBusResponseDto bus;
    private Integer currentRouteStopSequence;
    private String tripStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GetBusRouteResponseDto getBusRoute() {
        return busRoute;
    }

    public void setBusRoute(GetBusRouteResponseDto busRoute) {
        this.busRoute = busRoute;
    }

    public GetBusResponseDto getBus() {
        return bus;
    }

    public void setBus(GetBusResponseDto bus) {
        this.bus = bus;
    }

    public Integer getCurrentRouteStopSequence() {
        return currentRouteStopSequence;
    }

    public void setCurrentRouteStopSequence(Integer currentRouteStopSequence) {
        this.currentRouteStopSequence = currentRouteStopSequence;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 