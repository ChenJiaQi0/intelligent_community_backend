package com.soft2242.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.system.mapper.OwnerMapper;
import com.soft2242.system.entity.Owner;
import com.soft2242.system.service.OwnerService;
import org.springframework.stereotype.Service;

@Service
public class OwnerServiceImpl extends ServiceImpl<OwnerMapper, Owner> implements OwnerService {

}
