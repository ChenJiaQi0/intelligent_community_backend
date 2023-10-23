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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_vistor_log")
public class VistorLog extends BaseEntity {
      /**
     * 访客姓名
     */
      private String visitorName;

      /**
     * 访客电话
     */
      private String visitorPhone;

      /**
     * 授权时间
     */
      @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
      private Date authorizedTime;

      /**
     * 授权设备id
     */
      private Integer equipId;

      /**
     * 关联业主id
     */
      private Integer ownerId;
}
