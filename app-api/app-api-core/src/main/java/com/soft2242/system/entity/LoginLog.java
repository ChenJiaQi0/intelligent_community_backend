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
@TableName("tb_login_log")
public class LoginLog extends BaseEntity {
      /**
     * 登录人员
     */
      private String personnel;

      /**
     * 终端类型
     */
      private String terminalType;

      /**
     * 浏览器版本
     */
      private String browserVersion;

      /**
     * 操作系统
     */
      private String systemName;

      /**
     * 设备名称
     */
      private String deviceName;

      /**
     * mac地址
     */
      private String mac;

      /**
     * ip地址
     */
      private String ip;

      /**
     * 地址
     */
      private String address;
}
