package com.soft2242.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.soft2242.base.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @description:
 * @author: yk
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_complaint")
public class Complaint extends BaseEntity {

    /**
     * 投诉类型(0:物业服务，1:业主服务)
     */
    private int complaintId;

    /**
     * 所属小区
     */
    private Long communityId;

    /**
     * 投诉内容
     */
    private String complaintContent;

    /**
     * 投诉标题
     */
    private String complaintTitle;

    /**
     * 反馈内容
     */
    private String feedback;

    /**
     * 反馈人(0:物业客服 1:业主客服)
     */
    private Integer propertyId;

    /**
     * 图片
     */
    private String pictures;
}
