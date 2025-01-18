package com.mingyang.busmanagementsystem.model.dto.request.tripRecord;

import java.io.Serializable;
import java.time.LocalDateTime;

// Example of a request DTO
// {
//     "busRouteId": 1,
//     "busId": 1,
//     "currentRouteStopSequence": 1,
//     "tripStatus": "IN_PROGRESS",
//     "startTime": "2024-01-17T10:00:00",
//     "endTime": "2024-01-17T11:00:00"
// }
public class CreateTripRecordRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long busRouteId;
    private Long busId;
    private Integer currentRouteStopSequence;
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
} 