package com.soft2242.system.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.cache.RedisCache;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.base.common.util.SMSUtils;
import com.soft2242.system.entity.Account;
import com.soft2242.system.entity.Owner;
import com.soft2242.system.entity.Property;
import com.soft2242.system.entity.request.loginReq;
import com.soft2242.system.service.AccountService;
import com.soft2242.system.service.OwnerService;
import com.soft2242.system.service.PropertyService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册登录
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class LoginController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private AccountService accountService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private OwnerService ownerService;
    @Resource
    private PropertyService propertyService;
    @Resource
    private SMSUtils smsUtils;

    /**
     * 获取验证码
     */
    @PostMapping("/getCode")
    public Result getCode(@RequestBody loginReq req) throws Exception {
        log.info("手机号是：" + req.getPhone());
        if (redisCache.get(req.getPhone()) != null){
            throw new ServerException("不允许重复发送");
        }

        String code = RandomStringUtils.randomNumeric(4);
        log.info("发送的验证码是:" + code);
        smsUtils.sendMsg(req.getPhone(), code);

        redisCache.set(req.getPhone(), code, 300L);
        return Result.ok();
    }

    /**
     * 账号密码登录
     */
    @PostMapping("/login")
    public Result login(@RequestBody loginReq req) {
        QueryWrapper<Account> wp = new QueryWrapper<>();
        wp.eq("account", req.getPhone());
        Account account = accountService.getOne(wp);

        //业主身份
        if (req.getType() == 0){
            Integer code = req.getCode();
            //验证码登录
            if (code != null){
                if (redisCache.get(req.getPhone()) == null) throw new ServerException("验证码过期");
                if (!code.toString().trim().equals(redisCache.get(req.getPhone()).toString().trim())) throw new ServerException("验证码错误");

                //未注册过的账号自动注册
                if (account == null){
                    account = new Account();
                    account.setAccount(req.getPhone());
                    account.setSalt(1);
                    account.setPassword("e3b26461547fd67414fe44260a510499");
                    boolean flag = accountService.save(account);
                    if (!flag) throw new ServerException("注册账号错误");

                    Map<String, Object> map = new HashMap<>();
                    map.put("phone", req.getPhone());
                    map.put("type", 0);
                    String jwt = "";
                    try {
                        jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                    } catch (Exception e) {
                        throw new ServerException("服务器异常");
                    }
                    return Result.ok(jwt);
                }else {
                    if (account.getIdentity() == null){
                        //游客
                        Map<String, Object> map = new HashMap<>();
                        map.put("phone", req.getPhone());
                        map.put("type", 0);
                        String jwt = "";
                        try {
                            jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                            map.put("token", jwt);
                        } catch (Exception e) {
                            throw new ServerException("服务器异常");
                        }
                        return Result.ok(map);
                    } else {
                        //注册过的业主
                        QueryWrapper<Owner> wp2 = new QueryWrapper<>();
                        wp2.eq("id", account.getOwnerId());
                        Owner loginOwner = ownerService.getOne(wp2);

                        Map<String, Object> map = new HashMap<>();
                        map.put("owner", loginOwner);
                        String jwt = "";
                        try {
                            jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                            map.put("token", jwt);
                        } catch (Exception e) {
                            throw new ServerException("服务器异常");
                        }
                        return Result.ok(map);
                    }

                }
            }else {
                //密码登录
                String password = req.getPassword();
                String md5Pass = SaSecureUtil.md5BySalt(password, String.valueOf(account.getSalt()));
                if (!md5Pass.equals(account.getPassword())) throw new ServerException("账号密码不正确");

                if (account.getIdentity() == null){
                    //游客
                    Map<String, Object> map = new HashMap<>();
                    map.put("phone", req.getPhone());
                    map.put("type", 0);
                    String jwt = "";
                    try {
                        jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                        map.put("token", jwt);
                    } catch (Exception e) {
                        throw new ServerException("服务器异常");
                    }
                    return Result.ok(map);
                } else {
                    //注册过的业主
                    QueryWrapper<Owner> wp2 = new QueryWrapper<>();
                    wp2.eq("id", account.getOwnerId());
                    Owner loginOwner = ownerService.getOne(wp2);

                    Map<String, Object> map = new HashMap<>();
                    map.put("owner", loginOwner);
                    String jwt = "";
                    try {
                        jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                        map.put("token", jwt);
                    } catch (Exception e) {
                        throw new ServerException("服务器异常");
                    }
                    return Result.ok(map);
                }
            }
        }else { //物业身份
            //密码登录
            String password = req.getPassword();
            String md5Pass = SaSecureUtil.md5BySalt(password, String.valueOf(account.getSalt()));
            if (!md5Pass.equals(account.getPassword())) throw new ServerException("账号密码不正确");
            QueryWrapper<Property> wp3 = new QueryWrapper<>();
            wp3.eq("id", account.getPropertyId());
            Property loginProperty = propertyService.getOne(wp3);

            Map<String, Object> map = new HashMap<>();
            map.put("property", loginProperty);
            map.put("type", 1);
            String jwt = "";
            try {
                jwt = jwtUtil.createJWT("1", "1", 24 * 60 * 60 * 1000, map);
                map.put("token", jwt);
            } catch (Exception e) {
                throw new ServerException("服务器异常");
            }
            return Result.ok(map);
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/rePassword")
    public Result<String> rePassword(@RequestBody loginReq req){
        if (redisCache.get(req.getPhone()) == null) throw new ServerException("验证码过期");
        if (!req.getCode().toString().trim().equals(redisCache.get(req.getPhone()).toString().trim())) throw new ServerException("验证码错误");

        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("account", req.getPhone());
        Account account = accountService.getOne(wrapper);
        if (account == null) throw new ServerException("账号不存在请先注册");

        String md5Password = SaSecureUtil.md5BySalt(req.getNewPassword(), String.valueOf(account.getSalt()));

        account.setPassword(md5Password);
        log.info("账号信息" + account);
        boolean flag = accountService.updateById(account);
        if (!flag) throw new ServerException("修改密码异常");

        return Result.ok();
    }

    @GetMapping("/test")
    public void test(){
        Claims claims = jwtUtil.parseJWT("eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNqqVirIyM9LVbJSMrQwMzUysTC3NDZV0lEqqSwAChroKKVWFChZGZpZmBoZGBqamNYCAAAA__8.XTvCtJ1zJEmeKhwuBUXIxJceye8x4mFfF5zZqROxOdc");
        String phone = claims.get("phone", String.class);
        System.out.println(phone);
    }
}

