package cn.hk.common.resp;

import cn.hk.common.enums.RespEnums;
import lombok.Data;

public class GlobalResponse<T> {
    private String code;

    private String msg;

    private Boolean status;

    private T data;

    public GlobalResponse(){
    }

    private GlobalResponse(String code, String msg, Boolean status, T data) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    private GlobalResponse(RespEnums enums,Boolean status,T data){
        this(enums.getCode(),enums.getMessage(),true,data);
    }

    public static GlobalResponse success(){
        return success(null);
    }

    public static <T> GlobalResponse<T> success(T data){
        return new GlobalResponse(RespEnums.SUCCESS,true,data);
    }

    public static GlobalResponse fail(){
        return fail(RespEnums.FAIL);
    }

    public static GlobalResponse fail(RespEnums enums){
        return new GlobalResponse(enums,false,null);
    }

    public static GlobalResponse fail(String code,String msg){
        GlobalResponse response = new GlobalResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setStatus(false);
        return response;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
