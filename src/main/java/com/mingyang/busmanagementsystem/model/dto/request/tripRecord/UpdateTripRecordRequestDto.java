package com.mingyang.busmanagementsystem.model.dto.request.tripRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UpdateTripRecordRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer currentRouteStopSequence;
    private String tripStatus;
    private LocalDateTime endTime;

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

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
} 