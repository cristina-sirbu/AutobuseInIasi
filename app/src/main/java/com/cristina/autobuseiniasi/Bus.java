package com.cristina.autobuseiniasi;

public class Bus {
    String vehicleName;
    String vehicleLat;
    String vehicleLong;
    String vehicleDate;

    public Bus(String vehicleName, String vehicleLat, String vehicleLong, String vehicleDate) {
        this.vehicleName = vehicleName;
        this.vehicleLat = vehicleLat;
        this.vehicleLong = vehicleLong;
        this.vehicleDate = vehicleDate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleLat() {
        return vehicleLat;
    }

    public String getVehicleLong() {
        return vehicleLong;
    }

    public String getVehicleDate() {
        return vehicleDate;
    }
}
