package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
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
@TableName("tb_building")
public class Building extends BaseEntity {
      private Integer communityId;
      private String buildingName;

      private String buildingNumber;

      private Integer totalFloors;

      private Integer elementNumber;


      private String buildingStructure;

      private Integer buildingType;

      private String buildingUsage;

      private Integer totalHouses;

      private Date acceptanceDate;

      private Date completionDate;

      private String usageArea;

      private String buildingArea;

      private Integer status;

      private Integer buildingIdRole;

      private String remark;
}
