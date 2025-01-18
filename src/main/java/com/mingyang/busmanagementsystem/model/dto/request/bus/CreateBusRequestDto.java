package com.mingyang.busmanagementsystem.model.dto.request.bus;

import java.io.Serializable;

// Example of a request DTO
// {
// "plateNo": "123456",
// "carStatus": "available",
// "carType": "bus",
// "internalBus": true
// }
public class CreateBusRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String plateNo;
    private String carStatus;
    private String carType;
    private Boolean internalBus;

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Boolean getInternalBus() {
        return internalBus;
    }

    public void setInternalBus(Boolean internalBus) {
        this.internalBus = internalBus;
    }
}
