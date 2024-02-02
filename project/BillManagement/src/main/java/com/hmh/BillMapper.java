package com.hmh;

import com.hmh.VO.Bill;
import com.hmh.VO.BillStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BillMapper {
    public List<Bill> getBillsByPassengerId(Long passenger_id);
    public List<Bill> getBillsByDriverId(Long driver_id);
    public Bill getBillWithStatus4Passenger(Long passenger_id, BillStatus status);
    public Bill getBillWithStatus4Driver(Long driver_id, BillStatus status);
    public Bill getBillById(Long id);
    public void updateBillById(Bill bill);
    public void insertBill(Bill newBill);
    public void deleteBillById(Long id);
}
