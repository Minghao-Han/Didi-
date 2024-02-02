package com.hmh.Service;

import com.hmh.VO.Bill;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
//@FeignClient:微服务客户端注解,value:指定微服务的名字,这样就可以使Feign客户端直接找到对应的微服务
@FeignClient(value = "BILL-SERVICE-PROVIDER")
public interface BillServiceFeign {
    @GetMapping("/bill/{driverId}")
    public List<Bill> getMyBills(@PathVariable("driverId") Long driverId);

    @GetMapping("/order/{driverId}")
    public Bill receiveOrder(@PathVariable("driverId") Long driverId);

    @GetMapping("/order/ongoing/{driverId}")
    public Bill getOngoingOrder(@PathVariable("driverId") Long driverId);
}
