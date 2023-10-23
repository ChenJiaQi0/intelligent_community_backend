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
@TableName("tb_property_company")
public class PropertyCompany extends BaseEntity {
      /**
     * 物业公司名称
     */
      private String companyName;

      /**
     * 物业公司简称
     */
      private String companySimpleName;

      /**
     * 所在地区
     */
      private String location;

      /**
     * 物业公司地址
     */
      private String address;

      /**
     * 物业公司logo
     */
      private String logo;

      /**
     * 经纬度
     */
      private String latitudeAndLongitude;

      /**
     * 物业联系方式
     */
      private String contactInformation;

      /**
     * 备注信息
     */
      private String remark;
}
