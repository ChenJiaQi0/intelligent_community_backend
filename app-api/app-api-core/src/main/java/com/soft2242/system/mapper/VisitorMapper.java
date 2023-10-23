package com.soft2242.system.mapper;

import com.soft2242.system.entity.Visitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cjq
 * @since 2023-05-24
 */
@Mapper
public interface VisitorMapper extends BaseMapper<Visitor> {
    List<String> selectByUUID(@Param("ownerId") Integer ownerId, @Param("communityId") Integer communityId);
}
