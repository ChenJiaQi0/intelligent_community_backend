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
@TableName("tb_equip_type")
public class EquipType extends BaseEntity {
      /**
     * 分类名称
     */
      private String typeName;

      /**
     * 排序字段
     */
      private Integer sort;

      /**
     * 状态(0:禁用 1:启用)
     */
      private Integer state;
}
