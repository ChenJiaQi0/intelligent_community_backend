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
@TableName("tb_patrol_log")
public class PatrolLog extends BaseEntity {
      /**
     * 巡检人编号(与物业表关联外键)
     */
      private Integer inspectorId;

      /**
     * 巡检点位编号(外键)
     */
      private Integer inspectionPointsId;

      /**
     * 巡检分类(外键)
     */
      private Integer patrolType;

      /**
     * 巡检结果
     */
      private String inspectionResult;

      /**
     * 巡检照片(数组,逗号隔开)
     */
      private String inspectionPhotos;

      /**
     * 巡检次数
     */
      private Integer inspectionNumbers;
}
