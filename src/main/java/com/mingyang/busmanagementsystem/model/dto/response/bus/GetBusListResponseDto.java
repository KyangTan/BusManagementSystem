package com.mingyang.busmanagementsystem.model.dto.response.bus;

import java.util.List;

public class GetBusListResponseDto {
    private List<GetBusResponseDto> buses;

    public List<GetBusResponseDto> getBuses() {
        return buses;
    }

    public void setBuses(List<GetBusResponseDto> buses) {
        this.buses = buses;
    }
}
