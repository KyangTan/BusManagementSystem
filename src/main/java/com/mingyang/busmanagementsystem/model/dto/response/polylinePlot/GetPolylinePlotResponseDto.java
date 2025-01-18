package com.mingyang.busmanagementsystem.model.dto.response.polylinePlot;

import java.time.LocalDateTime;

public class GetPolylinePlotResponseDto {
    private Long id;
    private String polylineData;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPolylineData() {
        return polylineData;
    }

    public void setPolylineData(String polylineData) {
        this.polylineData = polylineData;
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