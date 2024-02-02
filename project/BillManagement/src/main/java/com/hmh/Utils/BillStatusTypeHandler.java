package com.hmh.Utils;

import com.hmh.VO.BillStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

@MappedTypes(com.hmh.VO.BillStatus.class)
@MappedJdbcTypes(JdbcType.TINYINT)
public class BillStatusTypeHandler extends BaseTypeHandler<BillStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, BillStatus billStatus, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,status2Num(billStatus));
    }

    @Override
    public BillStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return num2Status(rs.getInt(columnName));
    }

    @Override
    public BillStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return num2Status(rs.getInt(columnIndex));
    }

    @Override
    public BillStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return num2Status(cs.getInt(columnIndex));
    }


    private BillStatus num2Status(Integer statusNum){
        switch (statusNum){
            case 0 -> {
                return BillStatus.FINISHED;
            }
            case 1 -> {
                return BillStatus.ONGOING;
            }
            case 2 -> {
                return BillStatus.WAITING;
            }
            case 3 -> {
                return BillStatus.CANCELED;
            }
        }
        return BillStatus.ERROR;
    }
    private Integer status2Num(BillStatus billStatus){
        switch (billStatus){
            case FINISHED -> {
                return 0;
            }
            case ONGOING -> {
                return 1;
            }
            case WAITING -> {
                return 2;
            }
            case CANCELED -> {
                return 3;
            }
        }
        return 4;
    }
}
