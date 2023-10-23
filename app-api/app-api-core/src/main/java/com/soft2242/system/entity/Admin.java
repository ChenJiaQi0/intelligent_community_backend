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
@TableName("tb_admin")
public class Admin extends BaseEntity {
      /**
     * id
     */
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 管理员姓名
     */
      private String username;

      /**
     * 管理电话
     */
      private String phone;

      /**
     * 头像
     */
      private String avatar;

      /**
     * 所属物业公司id
     */
      private Integer companyId;
}
