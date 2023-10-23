package com.soft2242.system.util;

import com.soft2242.system.entity.Visitor;
import com.soft2242.system.service.VisitorService;
import jakarta.annotation.Resource;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;

public class QuartzJob implements Job {
    @Resource
    private VisitorService visitorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        List<Visitor> list = visitorService.list();
        for (Visitor visitor : list) {
            Date date = new Date();
            Date effeTime = new Date(visitor.getEffectiveTime() + visitor.getCreateTime().getTime());
            if (date.getTime() < effeTime.getTime()){
                visitor.setIsEffective(0);
                visitorService.updateById(visitor);
            }
        }
    }
}
