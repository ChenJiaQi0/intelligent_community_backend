package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.soft2242.base.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账号表
 *
 * @author cjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_account")
public class Account extends BaseEntity {
    private String account;

    private String password;

    private Integer salt;

    private Integer identity;

    private Integer adminId;

    private Integer ownerId;

    private Integer propertyId;
}
