package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.soft2242.base.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 物业表
 *
 * @author cjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_property")
public class Property extends BaseEntity {
    private String username;

    private String phone;

    private String avatar;

    private String telephone;

    private Integer communityId;

    private Integer accountId;

    private Integer companyId;
}
