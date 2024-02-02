package com.hmh.Service;

import com.hmh.DriverMapper;
import com.hmh.VO.DriverInfo;
import com.hmh.VO.DriverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverService {
    @Autowired
    DriverMapper driverMapper;

    public void newUser(String driver_name,String license_plate,String vehicle_model,String password){
        DriverInfo driverInfo = new DriverInfo(null,driver_name,license_plate,vehicle_model, DriverStatus.OFFLINE);
        driverMapper.newDriverInfo(driverInfo);
        driverMapper.newAccount(password,driverInfo.getId());
    }
    public boolean verifyPassword(Long id, String password){
        String gotPassword = driverMapper.getAccountById(id).getPassword();
        if (gotPassword.equals(password)) return true;
        return false;
    }
    public DriverInfo getDriverInfo(Long id){
        System.out.println(driverMapper.getDriverInfoById(id));
        return driverMapper.getDriverInfoById(id);
    }

    public void changeStatus(Long id,DriverStatus driverStatus){
        DriverInfo driverInfo = driverMapper.getDriverInfoById(id);
        driverInfo.setStatus(driverStatus);
        driverMapper.updateDriverInfo(driverInfo);
    }
    public boolean online(Long id){
        if (id==null) return false;
        DriverInfo driverInfo = driverMapper.getDriverInfoById(id);
        if (driverInfo.getStatus().equals(DriverStatus.OFFLINE)){
            driverInfo.setStatus(DriverStatus.WAITING);
            driverMapper.updateDriverInfo(driverInfo);
            return true;
        }
        return false;
    }
    public boolean offline(Long id){
        if (id==null) return false;
        DriverInfo driverInfo = driverMapper.getDriverInfoById(id);
        if (driverInfo.getStatus().equals(DriverStatus.WAITING)){
            driverInfo.setStatus(DriverStatus.OFFLINE);
            driverMapper.updateDriverInfo(driverInfo);
            return true;
        }
        return false;
    }
}
