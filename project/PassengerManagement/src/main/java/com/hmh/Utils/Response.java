package com.hmh.Utils;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.http.HttpStatus;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {
    private int status;
    private String message;
    private T data;

    private Response(int status, String message, T data)  {
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public Response(String ResponseStr){
        Response response = JSON.parseObject(ResponseStr,Response.class);
        this.status = response.getStatus();
        this.message = response.getMessage();
        this.data = (T) response.getData();
    }

    public static<T> String success(String msg,T data){
        return toJsonString(new Response(HttpStatus.SC_OK,msg,data));
    }

    public static String success(String msg){
        return toJsonString(new Response(HttpStatus.SC_OK,msg,null));
    }

    public static<T> String fail(String msg,T data){
        return toJsonString(new Response(HttpStatus.SC_BAD_REQUEST,msg,data));
    }

    public static String fail(String msg){
        return toJsonString(new Response(HttpStatus.SC_BAD_REQUEST,msg,null));
    }
    public static String toJsonString(Object obj){
        return JSON.toJSONString(obj);
    }
}
