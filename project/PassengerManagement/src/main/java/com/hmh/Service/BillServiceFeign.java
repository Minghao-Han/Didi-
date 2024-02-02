package com.hmh.Service;

import com.hmh.VO.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
//@FeignClient:微服务客户端注解,value:指定微服务的名字,这样就可以使Feign客户端直接找到对应的微服务
@FeignClient(value = "BILL-SERVICE-PROVIDER")
public interface BillServiceFeign {
    @PostMapping("/trip/call")
    public Bill callTaxi(@RequestBody Bill newBill);
    @GetMapping("/trip/{passenger_id}")
    public Bill checkCurrentTrip(@PathVariable("passenger_id") Long passenger_id);
    @DeleteMapping("/trip/{bill_id}")
    public Integer cancelWaitingTrip(@PathVariable("bill_id") Long bill_id);
}
