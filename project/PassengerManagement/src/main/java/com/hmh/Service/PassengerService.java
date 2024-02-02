package com.hmh.Service;

import com.hmh.PassengerMapper;
import com.hmh.VO.PassengerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PassengerService {
    @Autowired
    PassengerMapper passengerMapper;

    public void newUser(String userName,String password){
        PassengerInfo passengerInfo = new PassengerInfo(null,userName);
        passengerMapper.newPassengerInfo(passengerInfo);
        passengerMapper.newAccount(password,passengerInfo.getId());
    }
    public boolean verifyPassword(Long id, String password){
        String gotPassword = passengerMapper.getAccountById(id).getPassword();
        if (gotPassword.equals(password)) return true;
        return false;
    }
    public PassengerInfo getPassengerInfo(Long id){
        return passengerMapper.getPersonalInfoById(id);
    }
    public PassengerInfo getPassengerInfo(String userName){
        return passengerMapper.getPersonalInfoByUsername(userName);
    }
}
