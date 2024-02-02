package com.hmh.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfo {
    private Long id;
    private String driver_name;
    private String license_plate;
    private String vehicle_model;
    private DriverStatus status;
}
