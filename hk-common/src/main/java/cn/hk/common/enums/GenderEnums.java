package cn.hk.common.enums;

public enum GenderEnums {

    MAN(0,"man"),
    WOMAN(1,"woman"),
    SECRECY(2,"secrecy");

    private int code;

    private String desc;

    GenderEnums(int code,String desc){
        this.code=code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
