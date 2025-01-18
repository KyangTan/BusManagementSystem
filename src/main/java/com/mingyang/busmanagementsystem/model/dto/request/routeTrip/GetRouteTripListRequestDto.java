package com.mingyang.busmanagementsystem.model.dto.request.routeTrip;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GetRouteTripListRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long busRouteId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Long startRouteStopId;

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