package com.mingyang.busmanagementsystem.model.dto.response.bus;

public class GetBusResponseDto {
    private Long id;
    private String plateNo;
    private String carStatus;
    private String carType;
    private Boolean internalBus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
