package cn.hk.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description users
 * @author MessageBring
 * @date 2023-02-10
 */
@Data
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.INPUT)
    /**
     * 自增主键，记录用户数
     */
    private Long userId;

    private String email;

    private String phone;

    /**
     * 用户昵称，长度限制32个字符
     */
    private String nickName;

    private String password;

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

    /**
     * 用户是否已注销，0:未注销，1：已注销
     */
    private int isDeleted;

    /**
     * date_created
     */
    private Date dateCreated;

    /**
     * date_updated
     */
    private Date dateUpdated;

    public Users() {}
}
