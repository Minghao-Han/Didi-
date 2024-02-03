package com.hmh.Service;

import com.hmh.VO.Bill;
import com.hmh.VO.BillStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BillServiceFeignTest {
    @Autowired
    BillServiceFeign billServiceFeign;

    @Test
    void callTaxi() {

    }

    @Test
    void checkCurrentTrip() {
        Bill expectBill = new Bill(Long.valueOf(3), Long.valueOf(1), null, LocalDateTime.parse("2024-02-02T21:02:52"), null, null, "Haikou", "Wuhan", BillStatus.WAITING);
        assertEquals(billServiceFeign.checkCurrentTrip(Long.valueOf(1)),expectBill,"doesn't equal");
    }

    @Test
    void cancelWaitingTrip() {
    }
}