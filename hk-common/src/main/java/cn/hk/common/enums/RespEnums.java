package cn.hk.common.enums;

public enum RespEnums {
    //成功响应
    SUCCESS("000000","success"),
    //未查到对应数据，
    NO_DATA("000001", "no data"),
    //失败响应
    FAIL("100000","fail"),
    //登录密码错误
    INCORRECT_PASS("100001","incorrect password"),
    //服务器错误
    SERVER_ERROR("100500","server error"),
    //参数校验不通过
    INVALID_PARAM("100400","invalid param"),
    //参数缺失
    MISSING_PARAM("100401","missing param"),
    //token校验未通过
    TOKEN_INVALID("100403","invalid token"),

    OVER_LOG_ERR_COUNT("100501","The number of wrong passwords exceeds 5 times, please try again tomorrow or contact customer service"),

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
