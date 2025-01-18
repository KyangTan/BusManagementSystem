package com.mingyang.busmanagementsystem.model.dto.response.routeTrip;

import java.time.LocalDateTime;

public class GetRouteTripResponseDto {
    private Long id;
    private Long busRouteId;
    private Long startRouteStopId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(Long busRouteId) {
        this.busRouteId = busRouteId;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getStartRouteStopId() {
        return startRouteStopId;
    }

    public void setStartRouteStopId(Long startRouteStopId) {
        this.startRouteStopId = startRouteStopId;
    }
} 