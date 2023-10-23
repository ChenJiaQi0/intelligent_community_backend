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
@TableName("tb_visitor")
public class Visitor extends BaseEntity implements Serializable{
      private static final long serialVersionUID = 1970653423088169401L;
      /**
     * 所属小区
     */
      private Integer communityId;

      /**
     * 授权房号
     */
      private Integer roomId;

      /**
     * 授权人
     */
      private Integer authorizer;

      /**
     * 有效时间
     */
      private Long effectiveTime;

      /**
     * 授权设备
     */
      private String authorizedDevice;

      /**
     * 开门次数
     */
      private Integer openingTimes;

      /**
     * 是否失效
     */
      private Integer isEffective;
      private String uuid;
}
