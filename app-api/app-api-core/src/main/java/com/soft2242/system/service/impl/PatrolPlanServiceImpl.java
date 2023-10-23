package com.soft2242.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.system.entity.PatrolPlanEntity;
import com.soft2242.system.mapper.PatrolPlanDao;
import com.soft2242.system.service.PatrolPlanService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 巡检计划
 *
 * @author xzx zixin02700@gmail.com
 * @since 1.0.0 2023-06-02
 */
@Service
@AllArgsConstructor
public class PatrolPlanServiceImpl extends ServiceImpl<PatrolPlanDao, PatrolPlanEntity> implements PatrolPlanService {

//    @Override
//    public PageResult<PatrolPlanVO> page(PatrolPlanQuery query) {
//        IPage<PatrolPlanEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
//
//        return new PageResult<>(PatrolPlanConvert.INSTANCE.convertList(page.getRecords()), page.getTotal());
//    }
//
//    private LambdaQueryWrapper<PatrolPlanEntity> getWrapper(PatrolPlanQuery query){
//        LambdaQueryWrapper<PatrolPlanEntity> wrapper = Wrappers.lambdaQuery();
//        return wrapper;
//    }
//
//    @Override
//    public void save(PatrolPlanVO vo) {
//        PatrolPlanEntity entity = PatrolPlanConvert.INSTANCE.convert(vo);
//
//        baseMapper.insert(entity);
//    }
//
//    @Override
//    public void update(PatrolPlanVO vo) {
//        PatrolPlanEntity entity = PatrolPlanConvert.INSTANCE.convert(vo);
//
//        updateById(entity);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void delete(List<Long> idList) {
//        removeByIds(idList);
//    }

}