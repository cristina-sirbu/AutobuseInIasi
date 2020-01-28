package com.cristina.autobuseiniasi;

public class Bus {
    String vehicleName;
    Double vehicleLat;
    Double vehicleLong;
    String vehicleDate;

    public Bus(String vehicleName, Double vehicleLat, Double vehicleLong, String vehicleDate) {
        this.vehicleName = vehicleName;
        this.vehicleLat = vehicleLat;
        this.vehicleLong = vehicleLong;
        this.vehicleDate = vehicleDate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public Double getVehicleLat() {
        return vehicleLat;
    }

    public Double getVehicleLong() {
        return vehicleLong;
    }

    public String getVehicleDate() {
        return vehicleDate;
    }
}
