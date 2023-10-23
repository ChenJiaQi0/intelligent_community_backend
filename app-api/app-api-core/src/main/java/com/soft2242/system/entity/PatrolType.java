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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_patrol_type")
public class PatrolType extends BaseEntity {
      /**
     * 社区编号(外键)
     */
      private Integer communityId;

      /**
     * 巡检分类名称
     */
      private String inspectionClassificationName;

      /**
     * 巡检分类图标
     */
      private String inspectionClassificationImg;

      /**
     * 是否显示(0:不显示 1:显示)
     */
      private Integer displayed;

      /**
     * 巡检状态(0:禁用 1:启用)
     */
      private Integer inspectionClassificationState;

      /**
     * 排序字段
     */
      private Integer sort;
}
