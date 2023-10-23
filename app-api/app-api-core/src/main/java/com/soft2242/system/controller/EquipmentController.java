package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Equipment;
import com.soft2242.system.entity.House;
import com.soft2242.system.service.EquipmentService;
import com.soft2242.system.service.HouseService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 *  设备
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class EquipmentController {
    @Autowired
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private HouseService houseService;

    /**
     * 获取设备
     *
     */
    @GetMapping("/getEquips")
    public Result getEquipments(@RequestParam(value = "owner_id", required = false) Integer ownerId,
                                @RequestParam(value = "property_id", required = false) Integer propertyId){
        //判断是否登录
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        String communityId = request.getHeader("community_id");
        if (ownerId != null){ //业主获取设备
            QueryWrapper<House> hwp = new QueryWrapper<>();
            hwp.eq("owner_id", ownerId);
            hwp.eq("community_id", communityId);
            List<House> houses = houseService.list(hwp);
            if (houses.size() == 0) return Result.ok();

            List<Integer> bIds = new ArrayList<>();
            for (House house : houses) {
                bIds.add(house.getBuildingId());
            }

            QueryWrapper<Equipment> ewp = new QueryWrapper<>();
            ewp.eq("community_id", communityId);
            ewp.in("building_id", bIds);
            List<Equipment> equips = equipmentService.list(ewp);
            if (equips.size() == 0) return Result.ok();
            return Result.ok(equips);
        }else if (propertyId != null){ //物业获取设备
            QueryWrapper<Equipment> ewp = new QueryWrapper<>();
            ewp.eq("community_id", communityId);
            List<Equipment> equips = equipmentService.list(ewp);
            return Result.ok(equips);
        }else {
            return Result.ok();
        }
    }

    @GetMapping("/getEquipsLikeName")
    public Result getEquipsLikeName(@RequestParam(value = "equip_name", required = false) String equipName){
        //判断是否登录
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        String communityId = request.getHeader("community_id");

        if (equipName != null){
            QueryWrapper<Equipment> ewp = new QueryWrapper<>();
            ewp.like("equip_name", equipName);
            ewp.eq("community_id", communityId);
            List<Equipment> list = equipmentService.list(ewp);
            return Result.ok(list);
        }
        QueryWrapper<Equipment> ewp = new QueryWrapper<>();
        ewp.eq("community_id", communityId);
        List<Equipment> list = equipmentService.list(ewp);
        return Result.ok(list);
    }
}
