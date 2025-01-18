package com.mingyang.busmanagementsystem.model.dto.request.polylinePlot;

import java.io.Serializable;

public class UpdatePolylinePlotRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String polylineData;

    public String getPolylineData() {
        return polylineData;
    }

    public void setPolylineData(String polylineData) {
        this.polylineData = polylineData;
    }
} 