package com.mingyang.busmanagementsystem.model.dto.response.routeStop;

import com.mingyang.busmanagementsystem.model.dto.response.busStop.GetBusStopResponseDto;

public class GetRouteStopResponseDto {
    private Long id;
    private GetBusStopResponseDto busStop;
    private Integer sequence;
    private Integer duration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GetBusStopResponseDto getBusStop() {
        return busStop;
    }

    public void setBusStop(GetBusStopResponseDto busStop) {
        this.busStop = busStop;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
