package com.hmh;

import com.hmh.Service.BillServiceFeign;
import com.hmh.Service.DriverService;
import com.hmh.Utils.JwtGenerator;
import com.hmh.Utils.Response;
import com.hmh.Utils.UserId;
import com.hmh.VO.Bill;
import com.hmh.VO.BillStatus;
import com.hmh.VO.DriverInfo;
import com.hmh.VO.DriverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DriverController {
    @Autowired
    DriverService driverService;
    @Autowired
    BillServiceFeign billServiceFeign;

    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        String driverName = requestBody.get("driver_name");
        String licensePlate = requestBody.get("license_plate");
        String vehicleModel = requestBody.get("vehicle_model");
        if (password.isEmpty()||driverName.isEmpty()||licensePlate.isEmpty()||vehicleModel.isEmpty())
            return Response.fail("please supply enough information");
        driverService.newUser(driverName,licensePlate,vehicleModel,password);
        return Response.success("successfully registered");
    }

    @GetMapping("/me")
    public String checkPersonalInfo(@UserId Long driverId){
        DriverInfo driverInfo = driverService.getDriverInfo(driverId);
        return Response.success("got personal info",driverInfo);
    }

    @GetMapping("/infoForPassenger/{driverId}")
    public DriverInfo getDriverInfoForPassenger(@PathVariable("driverId") Long driverId){
        if (driverId==null) return null;
        DriverInfo driverInfo = driverService.getDriverInfo(driverId);
        driverInfo.setStatus(null);
        return driverInfo;
    }

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> requestBody){
        String password = requestBody.get("password");
        String idStr = requestBody.get("id");
        if (idStr.isEmpty()||password.isEmpty()) return Response.fail("lack of password or username");
        Long id = Long.valueOf(idStr);

        if (driverService.verifyPassword(id, password)){
            String jwt = JwtGenerator.generate(id);
            return Response.success("successfully login",jwt);
        }
        return Response.fail("wrong password or username");
    }
    @DeleteMapping("/status")
    public String offline(@UserId Long driverId){
        driverService.offline(driverId);
        return Response.success("successfully offline");
    }
    @PostMapping("/status")
    public String online(@UserId Long driverId) {
        driverService.online(driverId);
        return Response.success("successfully online");
    }

    @GetMapping("/bill")
    public String getMyBills(@UserId Long driverId){
        List<Bill> myHistoryBills = billServiceFeign.getMyBills(driverId);
        return Response.success("got",myHistoryBills);
    }

    @GetMapping("/order")
    public String  receiveOrder(@UserId Long driverId){
        DriverStatus status = driverService.getDriverInfo(driverId).getStatus();
        if (status.equals(DriverStatus.OFFLINE)) return Response.fail("you are offline now, can't receive order");
        Bill bill = billServiceFeign.receiveOrder(driverId);
        if (bill == null) return Response.fail("no passengers");
        return Response.success("received an order",bill);
    }

    @GetMapping("/order/ongoing")
    public String getOngoingOrder(@UserId Long driverId){
        Bill bill = billServiceFeign.getOngoingOrder(driverId);
        if (bill == null) return Response.fail("no ongoing order");
        return Response.success("got",bill);
    }
    @PutMapping("/order")
    public String finishOrder(@UserId Long driverId){
        billServiceFeign.driverFinishOrder(driverId);
        finishOrder(driverId);
        return Response.success("finished order");
    }
}
