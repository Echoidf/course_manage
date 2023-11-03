package com.lgy.course.common;


import java.io.Serializable;

public class Response implements Serializable {
    private boolean success;
    private String message;
    private Object data;

    public static Response success(){
        Response restResponse = new Response();
        restResponse.setSuccess(true);
        return restResponse;
    }

    public static Response success(Object data){
        Response restResponse = new Response();
        restResponse.setData(data);

        return restResponse;
    }


    public static Response fail() {
        Response restResponse = new Response();
        restResponse.setSuccess(false);
        return restResponse;
    }

    public static Response fail(String message) {
        Response restResponse = new Response();
        restResponse.setMessage(message);
        restResponse.setSuccess(false);
        return restResponse;
    }

    public static Response fail(Integer code, String message) {
        Response restResponse = new Response();
        restResponse.setMessage(message);

        return restResponse;
    }

    public static Response fail(String message, Object data) {
        Response restResponse = new Response();
        restResponse.setData(data);

        return restResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
