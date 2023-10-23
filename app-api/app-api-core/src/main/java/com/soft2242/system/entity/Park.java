package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.soft2242.base.mybatis.entity.BaseEntity;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@TableName("tb_park")
public class Park extends BaseEntity {
      /**
     * 所属小区id
     */
      private Integer communityId;

      /**
     * 车位号
     */
      private String parkNo;

      /**
     * 车库区域
     */
      private String garageArea;

      /**
     * 车库类别
     */
      private String parkType;

      /**
     * 前缀名称
     */
      private String preName;

      /**
     * 车位面积(平方)
     */
      private String parkSize;

      /**
     * 起始编号
     */
      private Integer startNo;

      /**
     * 结束编号
     */
      private Integer endNo;

      /**
     * 备注
     */
      private String remark;
      private Integer carId;
      private Integer ownerId;
      private String parkName;
      private Integer hasCar;
      @TableField("private")
      private Integer isPrivate;
}
