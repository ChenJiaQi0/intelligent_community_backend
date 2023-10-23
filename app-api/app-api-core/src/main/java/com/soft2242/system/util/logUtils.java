package com.soft2242.system.util;

import com.soft2242.base.common.util.AddressUtils;
import com.soft2242.base.common.util.IpUtils;
import com.soft2242.system.entity.LoginLog;
import com.soft2242.system.service.LoginLogService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class logUtils {
    @Resource
    private LoginLogService LoginLogService;

    //插入日志
    public void generator(String personnel, HttpServletRequest request){
        //获取请求头信息
        String user_agent = request.getHeader("user-agent");

        LoginLog loginLog = new LoginLog();
        loginLog.setPersonnel(personnel);
        loginLog.setIp(IpUtils.getIpAddr(request));
        loginLog.setAddress(AddressUtils.getAddressByIp(loginLog.getIp()));
        loginLog.setTerminalType(user_agent);
        loginLog.setSystemName(user_agent);
        loginLog.setDeviceName(user_agent);
        loginLog.setBrowserVersion(user_agent);
        loginLog.setMac("E8-4E-06-51-3B-16");

        LoginLogService.save(loginLog);

        log.info("日志信息：" + loginLog);
    }
}
