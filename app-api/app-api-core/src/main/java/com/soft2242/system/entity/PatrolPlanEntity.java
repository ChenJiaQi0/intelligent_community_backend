package com.soft2242.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import com.soft2242.base.mybatis.entity.BaseEntity;


/**
 * 巡检计划
 */
@EqualsAndHashCode(callSuper=false)
@Data
@TableName("tb_patrol_plan")
public class PatrolPlanEntity extends BaseEntity {

	/**
	* 所属小区
	*/
	private Integer communityId;

	/**
	* 巡更计划名称
	*/
	private Integer planId;

	/**
	* 计划名称
	*/
	private String planName;

	/**
	* 巡检时间
	*/
	private String patrolTime;



	/**
	* 巡更项目
	*/
	private String patrolProject;

	/**
	* 备注
	*/
	private String remark;

	/**
	* 状态（0：启用1：禁用）
	*/
	private Integer state;

	/**
	* 操作(0：编辑1：删除)
	*/
	private Integer operation;


	/**
	* 巡检周期
	*/
	private String patrolCycle;

}