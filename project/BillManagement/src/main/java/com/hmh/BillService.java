package com.hmh;

import com.hmh.VO.Bill;
import com.hmh.VO.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

@Service
public class BillService {
    @Autowired
    BillMapper billMapper;

    //待接单订单
    ArrayBlockingQueue<Bill> waitingBills = new ArrayBlockingQueue<Bill>(100);

    public Bill getOngoingOrder4Passenger(Long passenger_id){
        return billMapper.getBillWithStatus4Passenger(passenger_id, BillStatus.ONGOING);
    }
    public Bill getWaitingOrder4Passenger(Long passenger_id){
        return billMapper.getBillWithStatus4Passenger(passenger_id, BillStatus.WAITING);
    }
    public Bill getOngoingOrder4Driver(Long driver_id){
        return billMapper.getBillWithStatus4Driver(driver_id, BillStatus.ONGOING);
    }

    public List<Bill> getHistoryBill4Passenger(Long passenger_id){
        return billMapper.getBillsByPassengerId(passenger_id);
    }
    public List<Bill> getHistoryBill4Driver(Long driver_id){
        return billMapper.getBillsByDriverId(driver_id);
    }

//    4 -> for,司机接单
    public Bill receiveOrder4Driver(Long driver_id){
        try {
            Bill receivedBill = waitingBills.poll(50, TimeUnit.MILLISECONDS);
            receivedBill.setDriver_id(driver_id);
            receivedBill.setStart_time(new Date());
            receivedBill.setStatus(BillStatus.ONGOING);
            billMapper.updateBillById(receivedBill);
            return receivedBill;
        }catch (EmptyStackException e){
            return null;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
//    乘客下单
    public Bill placeOrder4Passenger(Long passenger_id,String from, String to){
        Bill newBill = new Bill(null,passenger_id,null,null,null,null,from,to,BillStatus.WAITING);
        billMapper.insertBill(newBill);
        new Thread(()->{
            try {
                waitingBills.put(newBill);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return newBill;
    }
    public Integer cancelOrder4Passenger(Long bill_id){
        Bill billById = billMapper.getBillById(bill_id);
        if (!billById.getStatus().equals(BillStatus.WAITING))
            return 0;//0 代表司机已接单
        billMapper.deleteBillById(bill_id);
        return 1;//1代表已取消订单
    }


}
