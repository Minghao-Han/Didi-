package com.hmh.Service;

import com.hmh.VO.DriverInfo;
import com.hmh.VO.DriverStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DriverServiceTest {
    @Autowired
    DriverService driverService;

    @Test
    void newUser() {
        driverService.newUser("Elsa","ARENDELLE01","carriage","123456");
    }

    @Test
    void verifyPassword() {
        assertTrue(driverService.verifyPassword(Long.valueOf(2),"123456"),"login function error");
    }

    @Test
    void getDriverInfo() {
        DriverInfo expectDriverInfo = new DriverInfo(Long.valueOf(1), "Minghao Han", "鄂AS1269", "白色雪铁龙C6", DriverStatus.OFFLINE);
        assertEquals(expectDriverInfo,driverService.getDriverInfo(Long.valueOf(1)),"get info wrong");
    }

    @Test
    void changeStatus() {
        driverService.changeStatus(Long.valueOf(1),DriverStatus.COMING);
    }

    @Test
    void online() {
        driverService.online(Long.valueOf(1));
    }

    @Test
    void offline() {
        driverService.offline(Long.valueOf(1));
    }

    @Test
    void finishOrder() {
        driverService.finishOrder(Long.valueOf(1));
    }
}