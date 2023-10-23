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
@TableName("tb_repair")
public class Repair extends BaseEntity {
      /**
     * 报修类别(0:公共报修 1:个人报修)
     */
      private Integer repairId;

      /**
     * 所属小区
     */
      private Integer communityId;

      /**
     * 报修房号
     */
      private Integer roomId;

      /**
     * 报修人
     */
      private String repairer;

      /**
     * 报修内容
     */
      private String repairContent;

      /**
     * 图片
     */
      private String pictures;

      /**
     * 处理人（物业id）
     */
      private Integer handler;

      /**
     * 状态(0:未处理 1:处理中 2:已处理)
     */
      private Integer state;

      /**
     * 是否为投诉
     */
      private Integer iscomplaint;

      /**
     * 投诉类型(0:扰民投诉 1:物业投诉 2:卫生投诉 3:安全投诉)
     */
      private Integer complaintType;
}
