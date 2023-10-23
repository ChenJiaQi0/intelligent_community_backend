package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.soft2242.base.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 业主表
 *
 * @author cjq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_owner")
public class Owner extends BaseEntity {
    private String nickname;

    private String phone;

    private String username;

    private String idCard;

    private Integer gender;

    private Date birth;

    private String nPlace;

    private String nationality;

    private String politicalStatus;

    private Integer marriage;

    private Integer liveType;

    private Integer aType;

    private Integer trCard;

    private String address;

    private String avatar;

    private Integer isOwner;

    private Integer associated;

    private Integer isreviewed;

    private Integer parkId;
}
