package com.hmh;

import com.hmh.VO.DriverAccount;
import com.hmh.VO.DriverInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DriverMapper {
    //    id refers to driver's id
    public DriverAccount getAccountById(Long id);
    public DriverInfo getDriverInfoById(Long id);
    public void updateDriverInfo(DriverInfo driverInfo);
    public void newAccount(String password,Long id);
    public Long newDriverInfo(DriverInfo driverInfo);
}
