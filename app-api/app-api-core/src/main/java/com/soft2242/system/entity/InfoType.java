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
@TableName("tb_info_type")
public class InfoType extends BaseEntity {
      /**
     * 分类（外键）
     */
      private Integer communityId;

      /**
     * 分类字段
     */
      private Integer sort;

      /**
     * 分类名
     */
      private String typeName;

      /**
     * 分类图片
     */
      private String typePic;

      /**
     * 是否推荐(0:不推荐1:推荐)
     */
      private Integer recommend;

      /**
     * 显示(0:不显示1:显示)
     */
      private Integer display;

      /**
     * 状态(0:禁用1:启用)
     */
      private Integer state;
}
