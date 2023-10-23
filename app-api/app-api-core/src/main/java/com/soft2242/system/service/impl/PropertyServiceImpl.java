package com.soft2242.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.system.mapper.PropertyMapper;
import com.soft2242.system.entity.Property;
import com.soft2242.system.service.PropertyService;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

}
