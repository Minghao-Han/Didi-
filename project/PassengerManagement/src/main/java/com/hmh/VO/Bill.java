package com.hmh.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    private Long id;
    private Long passenger_id;
    private Long driver_id;
    private Date start_time;
    private Date arrive_time;
    private Double fare;
    private String from;
    private String to;
    private BillStatus status;
}
