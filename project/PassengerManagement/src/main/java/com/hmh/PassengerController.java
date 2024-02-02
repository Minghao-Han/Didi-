package com.hmh;

import com.hmh.Service.BillServiceFeign;
import com.hmh.Service.DriverServiceFeign;
import com.hmh.Service.PassengerService;
import com.hmh.Utils.JwtGenerator;
import com.hmh.Utils.UserId;
import com.hmh.VO.Bill;
import com.hmh.VO.DriverInfo;
import com.hmh.VO.PassengerInfo;
import com.hmh.Utils.Response;
import com.hmh.VO.TripInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class PassengerController {
    @Autowired
    PassengerService passengerService;
    @Autowired
    BillServiceFeign billServiceFeign;

    @Autowired
    DriverServiceFeign driverServiceFeign;

    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        String userName = requestBody.get("user_name");
        passengerService.newUser(userName,password);
        return Response.success("successfully registered");
    }

    @GetMapping("/info")
    public String checkPersonalInfo(@UserId Long passengerId){
        System.out.println();
        PassengerInfo passengerInfo = passengerService.getPassengerInfo(passengerId);
        return Response.success("got personal info",passengerInfo);
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        String userName = requestBody.get("user_name");
        Long id = passengerService.getPassengerInfo(userName).getId();

        if (passengerService.verifyPassword(id, password)){
            String jwt = JwtGenerator.generate(id);
            return Response.success("successfully login",jwt);
        }
        return Response.fail("wrong password or username");
    }
    @GetMapping("/test")
    public String test(){
        System.out.println("enter test");
        return Response.success("hello_world");
    }

    @PostMapping("/trip")
    public String callTaxi(@RequestBody Map<String,String> requestBody,@UserId Long passengerId){
        String from = requestBody.get("from");
        String to = requestBody.get("to");
        if (from==null||to==null) return Response.fail("missing destination and origin");
        Bill newBill = new Bill();
        newBill.setPassenger_id(passengerId);
        newBill.setTo(to);
        newBill.setFrom(from);
        newBill = billServiceFeign.callTaxi(newBill);
        return Response.success("called taxi, the bill id is",newBill.getId());
    }
    @GetMapping("/trip")
    public String checkCurrentTrip(@UserId Long passengerId){
        Bill currentTrip = billServiceFeign.checkCurrentTrip(passengerId);
        if (currentTrip==null) return Response.success("no ongoing trip");
        DriverInfo driverInfo = null;
        if (currentTrip.getDriver_id()!=null)
            driverInfo= driverServiceFeign.getDriverInfoForPassenger(currentTrip.getDriver_id());
        return Response.success("got",new TripInfo(currentTrip,driverInfo));
    }
    @DeleteMapping("/trip/{bill_id}")
    public String cancelWaitingTrip(@PathVariable("bill_id") Long billId){
        if (billServiceFeign.cancelWaitingTrip(billId).equals(0))
            return Response.fail("driver is on the way, you can't cancel it");
        return Response.success("canceled");
    }
}
