package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("tb_house")
public class House extends BaseEntity {
      private Integer communityId;
      private Integer ownerId;
      private Integer userId;
      private Integer unit;
      private Integer buildingId;
      private Integer propertyType;
      private Integer roomNo;
      private Integer usageArea;
      private Integer buildingArea;
      private String houseType;
      private Integer sharedArea;
      private Integer storageRoomNumber;
      private String housingToward;
      private Integer parkingNo;
      private Integer isDefault;
      private String remark;
      private String ownerUserType;
}
