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
@TableName("tb_patrol_project")
public class PatrolProject extends BaseEntity {
      /**
     * 社区编号(外键)
     */
      private Integer communityId;

      /**
     * 计划名称
     */
      private String projectName;

      /**
     * 计划开始日期
     */
      private Date startDate;

      /**
     * 计划循环周期(天)
     */
      private Integer cycleTime;

      /**
     * 计划执行者(关联物业id)
     */
      private Integer projectExecutor;

      /**
     * 备注
     */
      private String remark;

      /**
     * 状态(0:禁用1:启用)
     */
      private Integer state;

}
