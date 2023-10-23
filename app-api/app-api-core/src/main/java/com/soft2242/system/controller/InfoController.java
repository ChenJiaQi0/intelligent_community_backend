package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Info;
import com.soft2242.system.service.InfoService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 社区活动
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class InfoController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private InfoService infoService;

    /**
     * 首页社区活动列表和对应详情
     */
    @GetMapping("/getCommunityList")
    public Result getCommunityList(@RequestParam(value = "info_id", required = false) Integer id){
        //判断是否登录
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        String communityId = request.getHeader("community_id");

        if (id == null){
            //查询对应小区对应的社区活动
            QueryWrapper<Info> wp = new QueryWrapper<>();
            wp.eq("community_id", communityId);
            List<Info> communityActivities = infoService.list(wp);
            if (communityActivities.size() == 0) return Result.ok();

            return Result.ok(communityActivities);
        }
        QueryWrapper<Info> wp = new QueryWrapper<>();
        wp.eq("community_id", communityId);
        wp.eq("id", id);
        Info info = infoService.getOne(wp);
        if (info == null) throw new ServerException("获取详情失败");
        return Result.ok(info);
    }
}
