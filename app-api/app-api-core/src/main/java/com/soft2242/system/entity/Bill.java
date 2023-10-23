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
@TableName("tb_bill")
public class Bill extends BaseEntity {
      /**
     * 所属房间
     */
      private Integer houseId;

      /**
     * 收费项目(数组,逗号分割,内容为项目id)[1,2]
     */
      private String project;

      /**
     * 截止日期
     */
      private Date deadline;

      /**
     * 是否预生成账单(0:否 1:是)
     */
      private Integer preGeneration;

      /**
     * 备注
     */
      private String remark;

      /**
     * 是否已交(0:否 1:是)
     */
      private Integer paid;
      private String year;
}
