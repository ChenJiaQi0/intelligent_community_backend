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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_equipment")
public class Equipment extends BaseEntity {
      /**
     * 关联小区外键
     */
      private Integer communityId;

      /**
       * 关联楼宇
       */
      private Integer buildingId;

      /**
     * 关联设备分类表外键
     */
      private Integer equipType;

      /**
     * 设备名称
     */
      private String equipName;

      /**
     * 设备位置
     */
      private String equipLocation;

      /**
     * 状态(0:不正常1:正常)
     */
      private Integer state;

      /**
     * 二维码
     */
      private String TDCode;

      /**
     * 故障率
     */
      private Integer failureRate;

      /**
       * 图片
       */
      private String img;
}
