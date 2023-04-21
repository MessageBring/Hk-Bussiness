package cn.hk.common.utils;



public class StringUtil {
    private static final String PHONE_REGX = "[1][3|4|5|7|8|9][0-9]{9}$";

    private static final String EMAIL_REGX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean isPhone(String phone) {
        if (phone==null||"".equals(phone)){
            return false;
        }
        return phone.matches(PHONE_REGX);
    }

    public static boolean isEmail(String email) {
        if (email==null||"".equals(email)){
            return false;
        }
        return email.matches(EMAIL_REGX);
    }

    public static boolean isEmpty(String str){
        return str == null || "".equals(str);
    }
}
