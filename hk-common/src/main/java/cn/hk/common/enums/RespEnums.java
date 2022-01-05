package cn.hk.common.enums;

public enum RespEnums {
    SUCCESS("000000","success"),

    FAIL("100001","fail"),

    SERVER_ERROR("100500","server error"),

    INVALID_PARAMS("100400","invalid params");

    private String code;

    private String message;


    RespEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
