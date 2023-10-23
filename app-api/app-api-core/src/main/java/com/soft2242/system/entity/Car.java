package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.soft2242.base.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/***
 * @description:
 * @author: yk
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_car")
public class Car extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌号
     */
    private String number;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 车辆型号
     */
    private String type;

    /**
     * 车辆
     */
    private String color;

    /**
     * 车辆保险截止日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date deadline;

    /**
     * 年审日期
     */
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date examineTime;

    private String icon;
    /**
     * 业主id
     */
    private Integer ownerId;


    /**
     * 创建时间
     */
    private Date createTime;

    private Integer parkId;
}