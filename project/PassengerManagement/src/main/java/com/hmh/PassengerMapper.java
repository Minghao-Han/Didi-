package com.hmh;

import com.hmh.VO.PassengerAccount;
import com.hmh.VO.PassengerInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PassengerMapper {
    //    id refers to passenger's id
    public PassengerAccount getAccountById(Long id);
    public PassengerInfo getPersonalInfoById(Long id);
    public PassengerInfo getPersonalInfoByUsername(String user_name);
    public void newAccount(String password,Long id);
    public Long newPassengerInfo(PassengerInfo passengerInfo);
}
