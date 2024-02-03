package com.hmh.Service;

import com.hmh.VO.PassengerInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassengerServiceTest {
    @Autowired
    PassengerService passengerService;

    @Test
    void newUser() {
        passengerService.newUser("Minghao Han","654321");
    }

    @Test
    void verifyPassword() {
        Long id = Long.valueOf(1);
        String password = "123456";
        assertTrue(passengerService.verifyPassword(id,password),"login function has some troubles");
    }

    @Test
    void getPassengerInfo() {
        System.out.println(passengerService.getPassengerInfo(Long.valueOf(1)));
    }

    @Test
    void testGetPassengerInfo() {
        PassengerInfo minghao_han = new PassengerInfo(Long.valueOf(3), "Minghao Han");
        assertEquals(minghao_han,passengerService.getPassengerInfo("Minghao Han"),"not equal");
    }
}