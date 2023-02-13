package cn.hk.common.utils;



public class StringUtil {
    private static final String PHONE_REGX = "[1][3|4|5|7|8|9][0-9]{9}$";

    private static final String EMAIL_REGX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    public static boolean checkPhone(String phone) {
        if (phone==null||"".equals(phone)){
            throw new IllegalArgumentException("Check phone param can't be empty");
        }
        return phone.matches(PHONE_REGX);
    }

    public static boolean checkEmail(String email) {
        if (email==null||"".equals(email)){
            throw new IllegalArgumentException("Check phone param can't be empty");
        }
        return email.matches(EMAIL_REGX);
    }
}
