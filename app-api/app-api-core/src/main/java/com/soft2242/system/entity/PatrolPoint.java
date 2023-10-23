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
@TableName("tb_patrol_point")
public class PatrolPoint extends BaseEntity {
      /**
     * 楼宇编号
     */
      private Integer buildingId;

      /**
     * 社区编号
     */
      private Integer communityId;

      /**
     * 区域选择 (0:室内1:室外)
     */
      private Integer regionSelection;

      /**
     * 巡检点名称
     */
      private String inspectionPointsName;

      /**
     * 巡检位置编号
     */
      private Integer inspectionLocationId;

      /**
     * 经纬度坐标
     */
      private String coordinate;

      /**
     * 定位距离
     */
      private Integer positioningDistance;

      /**
     * 拍照要求
     */
      private Integer photographyRequirements;

      /**
     * 巡检点位状态(0:禁用 1:启用)
     */
      private Integer inspectionPointsState;
      private Integer Status;
}
