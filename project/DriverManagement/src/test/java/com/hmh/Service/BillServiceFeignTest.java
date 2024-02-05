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
    void getMyBills() {
        Bill expectBill = new Bill(Long.valueOf(4), Long.valueOf(1), Long.valueOf(1), LocalDateTime.parse("2024-02-02T20:37:58"), null, null, "Haikou", "Wuhan", BillStatus.ONGOING);
        assertEquals(billServiceFeign.getMyBills(Long.valueOf(1)),expectBill,"no expect");
    }

    @Test
    void receiveOrder() {
        billServiceFeign.receiveOrder(Long.valueOf(1));
    }

    @Test
    void getOngoingOrder() {
        System.out.println(billServiceFeign.getOngoingOrder(Long.valueOf(1)));
    }

    @Test
    void driverFinishOrder() {
        billServiceFeign.driverFinishOrder(Long.valueOf(1));
    }
}