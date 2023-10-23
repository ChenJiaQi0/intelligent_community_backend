package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;
import com.soft2242.system.service.HouseService;
import com.soft2242.system.service.OwnerService;
import com.soft2242.system.service.RepairService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/***
 * 报修
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class RepairController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private HouseService houseService;
    @Resource
    private RepairService repairService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private OwnerService ownerService;

    /**
     * 获取报修列表及详情
     */
    @GetMapping("/getRepair")
    public Result<List<Repair>> getRepair(@RequestParam(value = "owner_id", required = false) Integer ownerId,
                                          @RequestParam(value = "property_id", required = false) Integer propertyId,
                                          @RequestParam(value = "repair_id", required = false) Integer repairId,
                                          @RequestParam(value = "type", required = false) Integer type){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        String communityId = request.getHeader("community_id");

        log.info(communityId);
        if (type == 0 && ownerId != null && repairId == null){
            Owner owner = ownerService.getById(ownerId);
            //获取业主对应房屋ids
            QueryWrapper<House> wp = new QueryWrapper<>();
            log.info(owner.toString());
            if (owner.getIsOwner() == 1) wp.eq("owner_id", owner.getId());
            else wp.eq("user_id", owner.getId());
            wp.eq("community_id", communityId);
            List<House> houses = houseService.list(wp);
            if (houses.size() == 0) return Result.ok();

            List<Integer> roomIds = new ArrayList<>();
            for (House house : houses) {
                roomIds.add(house.getId());
            }

            //获取业主房屋的报修信息
            QueryWrapper<Repair> wp2 = new QueryWrapper<>();
            wp2.in("room_id", roomIds);
            wp2.eq("iscomplaint", 0);
            wp2.eq("community_id", communityId);
            List<Repair> repairs = repairService.list(wp2);
            if (repairs.size() == 0) return Result.ok();
            log.info("该住户房屋报修信息：" + repairs);

            return Result.ok(repairs);
        }else if(type == 1 && repairId == null && propertyId !=null){ //物业获取该小区所有报修信息
            QueryWrapper<Repair> wp = new QueryWrapper<>();
            wp.eq("community_id", communityId);
            wp.eq("iscomplaint", 0);
            List<Repair> repairs = repairService.list(wp);
            if (repairs.size() == 0) return Result.ok();
            log.info("该小区所有房屋报修信息：" + repairs);
            return Result.ok(repairs);
        }else{
            return Result.ok();
        }
    }

    /**
     *  根据类型添加报修
     */
    @PostMapping("/addRepair")
    public Result addRepair(@RequestBody Repair repair){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        boolean flag = repairService.save(repair);
        if (!flag) throw new ServerException("报修失败");

        return Result.ok();
    }

    /**
     * 修改工单
     */
    @PostMapping("/updateWork")
    public Result updateWork(@RequestParam(value = "property_id",required = false) Integer propertyId,
                             @RequestParam(value = "repair_id",required = false) Integer repairId,
                             @RequestParam(value = "type", required = false) Integer type){
        Repair repair = null;
        if (repairId != null) {
            repair = repairService.getById(repairId);
        } else {
            throw new ServerException("查不到报修单");
        }

        if (type == 0){
            repair.setHandler(propertyId);
            repair.setState(0);
        } else if (type == 1) {
            repair.setState(1);
        }else if (type == 2){
            repair.setState(2);
        }
        boolean flag = repairService.updateById(repair);
        if (!flag) throw new ServerException("服务器异常");

        return Result.ok();
    }

    /**
     * 我的工单（物业端）
     */
    @GetMapping("/getWork")
    public Result getWork(@RequestParam("property_id") Integer id){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        QueryWrapper<Repair> rwp = new QueryWrapper<>();
        rwp.eq("handler", id);
        List<Repair> list = repairService.list(rwp);

        return Result.ok(list);
    }
}
