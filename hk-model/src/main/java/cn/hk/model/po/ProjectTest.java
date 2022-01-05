package cn.hk.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "project_test")
public class ProjectTest {
    @TableId(value = "test_id",type = IdType.AUTO)
    private Integer testId;

    private String testDesc;

    private Date dateCreate;

    private Date dateUpdate;

    private Boolean isDel;
}
