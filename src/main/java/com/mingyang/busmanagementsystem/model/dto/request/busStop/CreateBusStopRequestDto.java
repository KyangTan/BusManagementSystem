package com.mingyang.busmanagementsystem.model.dto.request.busStop;

// Example Request Body
// {
//     "name": "Bus Stop 1",
//     "shortName": "BS1",
//     "address": "123 Main St, City, Country",
//     "latitude": 12.3456,
//     "longitude": 78.9012,
//     "isHidden": false
// }

public class CreateBusStopRequestDto {
    private String name;
    private String shortName;
    private String address;
    private Double latitude;
    private Double longitude;
    private Boolean isHidden;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Boolean isHidden) {
        this.isHidden = isHidden;
    }
}
