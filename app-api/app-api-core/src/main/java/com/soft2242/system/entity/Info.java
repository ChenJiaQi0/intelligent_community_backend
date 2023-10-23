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
@TableName("tb_info")
public class Info extends BaseEntity {
      /**
     * 关联小区外键
     */
      private Integer communityId;

      /**
     * 资讯分类(关联分类表外键)
     */
      private Integer infoType;

      /**
     * 资讯名称
     */
      private String infoName;

      /**
     * 资讯描述
     */
      private String infoDescription;

      /**
     * 资讯内容
     */
      private String infoContent;

      /**
     * 缩略图
     */
      private String pictures;

      /**
     * 发布者(关联管理员)
     */
      private Integer adminId;

      /**
     * 开始时间
     */
      @JsonFormat(pattern = "yyyy-MM-dd")
      private Date startTime;

      /**
     * 结束时间
     */
      @JsonFormat(pattern = "yyyy-MM-dd")
      private Date endTime;

      /**
     * 是否推荐(0:不推荐1:推荐)
     */
      private Integer recommend;

      /**
     * 排序字段
     */
      private Integer sort;

      /**
     * 状态(0:隐藏 1:显示)
     */
      private Integer state;
}
