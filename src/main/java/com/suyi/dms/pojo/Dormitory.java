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
public class Dormitory implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 宿舍号
     */
    private Integer dno;

    /**
     * 宿舍规模
     */
    private Integer size;

    /**
     * 宿舍长学号
     */
    private String sno;

    /**
     * 宿舍长
     */
    private String sname;

    /**
     * 宿舍长联系方式
     */
    private String phone;

    /**
     * 楼层
     */
    private Integer floor;

    /**
     * 宿舍楼号
     */
    private Integer building;

    /**
     * 状态
     */
    private String state;

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
