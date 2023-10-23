package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("tb_community")
public class Community extends BaseEntity {
      private String name;

      private String location;

      private String locationDetail;

      private String floorArea;

      private String buildingArea;

      private String publicArea;

      private String greenArea;

      private String parkingArea;

      private String communityPictures;

      private String principalName;

      private String principalPhone;
      private String principalTelphone;

      private String remark;


}
