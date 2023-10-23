package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Complaint;
import com.soft2242.system.service.ComplaintService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * @description:
 * @author: yk
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class ComplaintController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private ComplaintService complaintService;

    /**
     添加投诉
    */
    @PostMapping("/addComplaint")
    public Result addComplaint(@RequestParam(value = "complaint_id",required = false) Integer complaintId,
                               @RequestParam(value = "owner_id",required = false) Integer ownerId,
                               @RequestBody Complaint complaint) {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) {
            throw new ServerException("没有该业主");
        }
        if (ownerId != null && complaintId == 0) {
            complaintService.save(complaint);
        }else if (ownerId != null && complaintId == 1)
            complaintService.save(complaint);
        return Result.ok();
    }

    @GetMapping("/getComplaint")
    public Result getComplaint(@RequestParam(value = "owner_id", required = false) Integer ownerId) {
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) {
            throw new ServerException("没有投诉的业主");
        }
        if (ownerId != null) {
            QueryWrapper<Complaint> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("community_id", communityId);
            queryWrapper.eq("owner_id", ownerId);
            List<Complaint> complaints = complaintService.list(queryWrapper);
//            List<Object> list = new ArrayList<>();
//            for (Complaint complaint : complaints) {
//                list.add(complaint);
//                log.info("list:"+list);
//            }
            log.info("complaints:"+complaints);
            return Result.ok(complaints);
        }
        return Result.error();
    }
}
