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
@TableName("tb_patrol_role")
public class PatrolRole extends BaseEntity {
      private Integer patrolType;
      /**
     * 社区编号(外键)
     */
      private Integer communityId;

      /**
     * 巡检路线名称(根据巡检点位)
     */
      private String inspectionRouteName;

      /**
     * 从哪个巡检点(外键)
     */
      private Integer fromPatrolPoint;

      /**
     * 到哪个巡检点(外键)
     */
      private Integer toPatrolPoint;

      /**
     * 巡检开始时间
     */
      private Date inspectionStartTime;

      /**
     * 巡检开始时间
     */
      private Date inspectionEndTime;

      /**
     * 精度坐标
     */
      private String precisionCoordinates;

      /**
     * 是否必须拍照(0:否1:是)
     */
      private Integer takePictures;

      /**
     * 巡检路线状态(0:禁用 1:启用)
     */
      private Integer inspectionRouteState;
}
