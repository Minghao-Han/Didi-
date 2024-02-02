package com.hmh;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * @author hanmingzhi
 * @date 2022/8/14 18:47
 **/
public class TokenUtil {
    //token到期时间30分钟(根据需求改)
    private static final long EXPIRE_TIME= 2*24*60*60*1000;
    private static final String PASSENGER_TOKEN_SECRET ="MD9**+4MG^EG79RV+passengerT?J87AI4NWQVT^&";

    private static final String DRIVER_TOKEN_SECRET ="MD9**+4MG^EG79RV+dirverT?J87AI4NWQVT^&";
    /** token验证 */
    public static Long checkPassengerToken(String token){
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(PASSENGER_TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            Long id = decodedJWT.getClaim("id").asLong();
            System.out.println("用户"+id+"认证通过，过期时间"+decodedJWT.getExpiresAt());
            return id;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return null;
        }
    }
    public static Long checkDriverToken(String token){
        try {
            //创建token验证器
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(DRIVER_TOKEN_SECRET)).withIssuer("auth0").build();
            DecodedJWT decodedJWT=jwtVerifier.verify(token);
            Long id = decodedJWT.getClaim("id").asLong();
            System.out.println("用户"+id+"认证通过，过期时间"+decodedJWT.getExpiresAt());
            return id;
        } catch (IllegalArgumentException | JWTVerificationException e) {
            //抛出错误即为验证不通过
            return null;
        }
    }

    public static boolean IdPermissionCheck(String token,int user_id){//验证用户权限，避免操作别人的页面
        JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(PASSENGER_TOKEN_SECRET)).withIssuer("auth0").build();
        DecodedJWT decodedJWT=jwtVerifier.verify(token);
        if ((decodedJWT.getClaim("id").asInt()).equals(user_id)) return true;
        else return false;
    }
//    public static int getUserIdFromToken(String token){
//        try {
//            //创建token验证器
//            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("auth0").build();
//            DecodedJWT decodedJWT=jwtVerifier.verify(token);
//            return decodedJWT.getClaim("id").asInt();
//        } catch (IllegalArgumentException | JWTVerificationException e) {
//            //抛出错误即为验证不通过
//            return 0;
//        }
//    }
}

