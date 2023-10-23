package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Notice;
import com.soft2242.system.service.NoticeService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 消息通知
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class NoticeController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private NoticeService noticeService;

    /**
     * 社区公告消息
     */
    @GetMapping("/getNotice")
    public Result getNotice(@RequestParam(value = "notice_id", required = false) Integer id){
        //判断是否登录
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        String communityId = request.getHeader("community_id");

        if (id == null){
            QueryWrapper<Notice> wp = new QueryWrapper<>();
            wp.eq("community_id", communityId);
            List<Notice> notices = noticeService.list(wp);
            if (notices.size() == 0) return Result.ok();

            return Result.ok(notices);
        }
        QueryWrapper<Notice> wp = new QueryWrapper<>();
        wp.eq("community_id", communityId);
        wp.eq("id", id);
        Notice notice = noticeService.getOne(wp);
        if (notice == null) throw new ServerException("获取详情失败");
        return Result.ok(notice);
    }
}
