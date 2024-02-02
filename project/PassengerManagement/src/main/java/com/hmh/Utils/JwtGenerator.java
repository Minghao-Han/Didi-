package com.hmh.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.Date;

public class JwtGenerator {
    //token到期时间30分钟
    private static final long EXPIRE_TIME= 2*24*60*60*1000;
    //密钥 (随机生成,可以从网上找到随机密钥生成器)

    private static final String ROLE = "passenger";
    private static final String TOKEN_SECRET="MD9**+4MG^EG79RV+passengerT?J87AI4NWQVT^&";
    /** 生成token */
    public static String generate(Long id){
        try {
            Date expireAt=new Date(System.currentTimeMillis()+EXPIRE_TIME);
            String token = JWT.create()
                    //发行人
                    .withIssuer("auth0")
                    .withClaim("role",ROLE)
                    //存放数据
//                    .withClaim("userName",user.getUserName())
                    .withClaim("id",id)
                    //过期时间
                    .withExpiresAt(expireAt)
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
            return token;
        } catch (IllegalArgumentException | JWTCreationException je) {
            return null;
        }
    }
}
