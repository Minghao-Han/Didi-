package com.hmh.Service;

import com.hmh.VO.DriverInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceFeignTest {

    @Autowired
    DriverServiceFeign driverServiceFeign;
    @Test
    void getDriverInfoForPassenger() {
        DriverInfo expectDriverInfo = new DriverInfo(Long.valueOf(1), "Minghao Han", "鄂AS1269", "白色雪铁龙C6", null);
        assertEquals(driverServiceFeign.getDriverInfoForPassenger(Long.valueOf(1)),expectDriverInfo,"wrong");
    }
}