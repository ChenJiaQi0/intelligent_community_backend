package com.soft2242.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.soft2242.system.entity.PatrolPlanEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* 巡检计划
*
* @author xzx zixin02700@gmail.com
* @since 1.0.0 2023-06-02
*/
@Mapper
public interface PatrolPlanDao extends BaseMapper<PatrolPlanEntity> {
	
}