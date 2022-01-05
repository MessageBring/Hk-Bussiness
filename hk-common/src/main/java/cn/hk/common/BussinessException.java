package cn.hk.common;

import lombok.Data;

@Data
public class BussinessException extends RuntimeException{
    private String code;

    private String errMsg;

    public BussinessException(String code, String errMsg) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }
}
