package com.hmh.Service;

import com.hmh.VO.DriverInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


@Service
//@FeignClient:微服务客户端注解,value:指定微服务的名字,这样就可以使Feign客户端直接找到对应的微服务
@FeignClient(value = "DRIVER-MANAGEMENT")
public interface DriverServiceFeign {
    @GetMapping("/infoForPassenger/{driverId}")
    public DriverInfo getDriverInfoForPassenger(@PathVariable("driverId") Long driverId);
}
