package cn.hk.common.resp;

import cn.hk.common.enums.RespEnums;
import lombok.Data;

public class GlobalResponse {
    private String code;

    private String msg;

    private Boolean status;

    private Object data;

    public GlobalResponse(){
    }

    public GlobalResponse(String code, String msg, Boolean status, Object data) {
        this.code = code;
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public GlobalResponse(RespEnums enums,Boolean status,Object data){
        this(enums.getCode(),enums.getMessage(),true,data);
    }

    public static GlobalResponse success(){
        return success(null);
    }

    public static GlobalResponse success(Object data){
        return new GlobalResponse(RespEnums.SUCCESS,true,data);
    }

    public static GlobalResponse fail(){
        return fail(RespEnums.FAIL);
    }

    public static GlobalResponse fail(RespEnums enums){
        return new GlobalResponse(enums,false,null);
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

    public void setData(Object data) {
        this.data = data;
    }
}
