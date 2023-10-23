package com.soft2242.system.service.impl;

import com.soft2242.system.entity.Visitor;
import com.soft2242.system.mapper.VisitorMapper;
import com.soft2242.system.service.VisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjq
 * @since 2023-05-24
 */
@Service
public class VisitorServiceImpl extends ServiceImpl<VisitorMapper, Visitor> implements VisitorService {

    @Override
    public List<String> selectByUUID(Integer ownerId, Integer communityId) {
        return baseMapper.selectByUUID(ownerId, communityId);
    }
}
