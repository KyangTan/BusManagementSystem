package com.mingyang.busmanagementsystem.model.dto.request.routeTrip;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UpdateRouteTripRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Long startRouteStopId;

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