package com.soft2242.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.system.entity.Car;
import com.soft2242.system.mapper.CarMapper;
import com.soft2242.system.service.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

}
