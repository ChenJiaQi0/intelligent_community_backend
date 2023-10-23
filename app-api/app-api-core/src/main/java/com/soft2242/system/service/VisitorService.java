package com.soft2242.system.service;

import com.soft2242.system.entity.Visitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjq
 * @since 2023-05-24
 */
public interface VisitorService extends IService<Visitor> {
    List<String> selectByUUID(Integer ownerId, Integer communityId);
}
