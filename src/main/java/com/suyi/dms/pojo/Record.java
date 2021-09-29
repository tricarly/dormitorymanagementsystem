package com.suyi.dms.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author suyi
 * @since 2021-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Record implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请学生学号
     */
    private String sno;

    /**
     * 申请学生姓名
     */
    private String sname;

    /**
     * 原宿舍号
     */
    private Integer dnobefore;

    /**
     * 转宿舍号
     */
    private Integer dnoafter;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 状态
     */
    private String state;

    /**
     * 批准意见
     */
    private String opinion;

    /**
     * 创建日期
     */
      @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新日期
     */
      @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
