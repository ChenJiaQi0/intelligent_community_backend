package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("tb_notice")
public class Notice extends BaseEntity {
      /**
     * 通知标题
     */
      private String title;

      /**
     * 所属小区
     */
      private Integer communityId;

      /**
     * 通知内容
     */
      private String content;

      /**
     * 接收范围(0:全体楼宇 1:指定楼宇)
     */
      private Integer range_type;

      /**
     * 提醒方式(0:系统消息 1:短信通知)
     */
      private Integer method;

      /**
     * 发布人
     */
      private String publisher;

      /**
     * 发布时间
     */
      @JsonFormat(pattern = "yyyy-MM-dd")
      private Date releaseTime;
}
