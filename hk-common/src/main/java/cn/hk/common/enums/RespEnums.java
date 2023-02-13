package cn.hk.common.enums;

public enum RespEnums {
    SUCCESS("000000","success"),

    NO_DATA("000001", "no data"),

    INCORRECT_PASS("000002","incorrect password"),

    FAIL("100000","fail"),

    SERVER_ERROR("100500","server error"),

    INVALID_PARAM("100400","invalid param"),

    MISSING_PARAM("100401","missing param"),

    TOKEN_INVALID("100403","invalid token"),

    ;

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
