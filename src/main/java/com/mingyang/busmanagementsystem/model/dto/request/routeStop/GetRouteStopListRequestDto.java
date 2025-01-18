package com.mingyang.busmanagementsystem.model.dto.request.routeStop;

import java.io.Serializable;

public class GetRouteStopListRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long routeId;

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
