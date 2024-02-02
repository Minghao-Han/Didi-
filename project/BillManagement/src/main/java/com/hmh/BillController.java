package com.hmh;

import com.hmh.Utils.Response;
import com.hmh.VO.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BillController {
    @Autowired
    BillService billService;
    @GetMapping("/bill/passenger/{passenger_id}")
    public List<Bill> getPassengerBills(@PathVariable("passenger_id") Long passenger_id){
        return billService.getHistoryBill4Passenger(passenger_id);
    }

    @PostMapping("/trip/call")
    public Bill callTaxi(@RequestBody Bill newBill){
//    newBill should at least contain Long passenger_id, String from,String to
        return billService.placeOrder4Passenger(newBill.getPassenger_id(), newBill.getFrom(), newBill.getTo());
    }

    @GetMapping("/trip/{passenger_id}")
    //If have a waiting trip, then return. Or return the ongoing bill.
    public Bill checkCurrentTrip(@PathVariable("passenger_id") Long passenger_id){
        Bill bill = billService.getWaitingOrder4Passenger(passenger_id);
        if (bill!=null) return bill;
        return billService.getOngoingOrder4Passenger(passenger_id);
    }
    @DeleteMapping("/trip/{bill_id}")
    public Integer cancelWaitingTrip(@PathVariable("bill_id") Long bill_id){
        return billService.cancelOrder4Passenger(bill_id);
    }

    @GetMapping("/bill/driver/{driverId}")
    public List<Bill> getMyBills(@PathVariable("driverId") Long driverId){
        return billService.getHistoryBill4Driver(driverId);
    }

    @GetMapping("/order/{driverId}")
    public Bill receiveOrder(@PathVariable("driverId") Long driverId){
        return billService.receiveOrder4Driver(driverId);
    }

    @GetMapping("/order/ongoing/{driverId}")
    public Bill getOngoingOrder(@PathVariable("driverId") Long driverId){
        return billService.getOngoingOrder4Driver(driverId);
    }
}
