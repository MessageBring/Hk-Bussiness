package cn.hk.common;

import cn.hk.common.enums.RespEnums;
import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private String code;

    private String errMsg;

    public BusinessException(String code, String errMsg) {
        super(errMsg);
        this.code = code;
        this.errMsg = errMsg;
    }

    public BusinessException(RespEnums enums){
        this(enums.getCode(),enums.getMessage());
    }
}
