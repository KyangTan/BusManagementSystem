package com.mingyang.busmanagementsystem.model.dto.request.tripRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

public class GetTripRecordListRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long busRouteId;
    private Long busId;
    private String tripStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Long getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(Long busRouteId) {
        this.busRouteId = busRouteId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
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
} 