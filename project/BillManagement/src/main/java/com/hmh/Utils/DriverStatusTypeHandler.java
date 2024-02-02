package com.hmh.Utils;

import com.hmh.VO.DriverStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(com.hmh.VO.DriverStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
public class DriverStatusTypeHandler extends BaseTypeHandler<DriverStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, DriverStatus driverStatus, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,status2Num(driverStatus));
    }

    @Override
    public DriverStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return num2Status(rs.getInt(columnName));
    }

    @Override
    public DriverStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return num2Status(rs.getInt(columnIndex));
    }

    @Override
    public DriverStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return num2Status(cs.getInt(columnIndex));
    }


    private DriverStatus num2Status(Integer statusNum){
        switch (statusNum){
            case 0 -> {
                return DriverStatus.OFFLINE;
            }
            case 1 -> {
                return DriverStatus.DELIVERING;
            }
            case 2 -> {
                return DriverStatus.WAITING;
            }
            case 3 -> {
                return DriverStatus.COMING;
            }
        }
        return DriverStatus.ERROR;
    }
    private Integer status2Num(DriverStatus driverStatus){
        switch (driverStatus){
            case OFFLINE -> {
                return 0;
            }
            case DELIVERING -> {
                return 1;
            }
            case WAITING -> {
                return 2;
            }
            case COMING -> {
                return 3;
            }
        }
        return 4;
    }
}
