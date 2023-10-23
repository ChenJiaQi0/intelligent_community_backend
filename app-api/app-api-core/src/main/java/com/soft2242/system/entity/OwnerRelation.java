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
@TableName("tb_owner_relation")
public class OwnerRelation extends BaseEntity {
      /**
     * 姓名
     */
      private String username;

      /**
     * 0:男 1:女
     */
      private Integer gender;

      /**
     * 手机号
     */
      private String phone;

      /**
     * 住址
     */
      private String address;

      /**
     * 关系
     */
      private String relation;

      /**
     * 关联业主表id
     */
      private Integer ownerId;
}
