package cn.hk.model.vo;

import lombok.Data;

@Data
public class UserVO {
    /**
     * 自增主键，记录用户数
     */
    private Long userId;

    /**
     * 用户昵称，长度限制32个字符
     */
    private String nickName;

    /**
     * 用户性别，0:女，1:男，2:保密
     */
    private int gender;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户登录错误次数，密码错误三次，五分钟内禁止登录
     */
    private Integer loginErrCnt;

    /**
     * 用户是否实名认证，0:未认证，1:已认证
     */
    private int isVerified;

    private String token;
}
