package com.soft2242.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.soft2242.system.entity.Complaint;
import com.soft2242.system.mapper.ComplaintMapper;
import com.soft2242.system.service.ComplaintService;
import org.springframework.stereotype.Service;

@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {

}
