package com.hmh.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TripInfo {
    private Long id;
    private Long passenger_id;
    private String driver_name;
    private String license_plate;
    private String vehicle_model;
    private Date start_time;
    private Date arrive_time;
    private Double fare;
    private String from;
    private String to;
    private BillStatus status;
    public TripInfo(Bill bill, DriverInfo driverInfo){
        this.id= bill.getId();
        this.passenger_id = bill.getPassenger_id();
        if (driverInfo!=null){
            this.license_plate = driverInfo.getLicense_plate();
            this.vehicle_model = driverInfo.getVehicle_model();
        }
        this.start_time = bill.getStart_time();
        this.arrive_time = bill.getArrive_time();
        this.fare = bill.getFare();
        this.from = bill.getFrom();
        this.to = bill.getTo();
        this.status = bill.getStatus();
    }
}
