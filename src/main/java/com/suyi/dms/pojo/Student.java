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
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    private String sno;

    /**
     * 姓名
     */
    private String sname;

    /**
     * 照片
     */
    private String picture;

    /**
     * 性别
     */
    private String sex;

    /**
     * 院系
     */
    private String department;

    /**
     * 专业
     */
    private String subject;

    /**
     * 班级
     */
    private String classname;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * qq
     */
    private String qq;

    /**
     * 宿舍号
     */
    private Integer dno;

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
