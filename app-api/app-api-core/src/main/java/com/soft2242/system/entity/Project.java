package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.soft2242.base.mybatis.entity.BaseEntity;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author cjq
 * @since 2023-05-24
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@TableName("tb_project")
public class Project extends BaseEntity {
      /**
     * 项目名称
     */
      private String projectName;

      /**
     * 项目类别(0:物业管理费用 1:其他费用)
     */
      private Integer projectType;

      /**
     * 计费方式(0:按建筑面积 1:按天)
     */
      private Integer chargeType;

      /**
     * 单位
     */
      private String unit;

      /**
     * 价格(元)
     */
      private Double price;

      /**
     * 精确等级(0:精确到分，1:精确到角,2:精确到元)
     */
      private Integer precisionLevel;

      /**
     * 账单周期(月)
     */
      private Integer billingCycle;

      /**
     * 违约金率(千分之)
     */
      private Integer rate;

      /**
     * 违约起算天数(天)
     */
      private Integer startDays;

      /**
     * 违约执行日期
     */
      private Date endDate;

      /**
     * 是否有优惠券(0:无 1:有)
     */
      private Integer hasCoupon;

      /**
     * 状态(0:不启用 1:启用)
     */
      private Integer state;
      private String icon;
      private String per;
}
