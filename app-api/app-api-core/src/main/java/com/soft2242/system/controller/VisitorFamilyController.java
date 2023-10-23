package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;

import com.soft2242.system.service.*;
import com.soft2242.system.util.JwtUtil;
 import com.soft2242.system.service.*;
import com.soft2242.system.vo.AddHouseVo;
import com.soft2242.system.vo.BuildingInfo;
import com.soft2242.system.vo.HouseInfo;
import com.soft2242.system.vo.RoomInfo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * @description:
 * @author: yk
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class VisitorFamilyController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private OwnerService ownerService;

    @Resource
    private HouseService houseService;

    @Resource
    private OwnerRelationService ownerRelationService;

    @Resource
    private CommunityService communityService;

    @Resource
    private BuildingService buildingService;

    /**
        绑定家属
    */
    @PostMapping("/addFamily")
    public Result addHouse(@RequestBody AddHouseVo houseVo) {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (houseVo.getOwnerId() == null) {
            throw new ServerException("该业主没有添加房屋信息");
        }
        if (houseVo.getOwnerId() != null) {
            Owner owner = new Owner();
            owner.setUsername(houseVo.getName());
            owner.setGender(houseVo.getGender());
            owner.setPhone(houseVo.getPhone());
            owner.setIdCard(houseVo.getIdentityCard());
            boolean save = ownerService.save(owner);
            if (!save) throw new ServerException("系统错误");

            QueryWrapper<House> wrapper = new QueryWrapper<>();
            wrapper.eq("community_id", houseVo.getCommunityId());
            wrapper.eq("building_id", houseVo.getBuildingId());
            wrapper.eq("unit", houseVo.getUnit());
            wrapper.eq("room_no", houseVo.getRoomNo());
            House house = houseService.getOne(wrapper);
            if (house == null) {
                throw new ServerException("房间信息没对应上");
            }else {
                house.setOwnerId(owner.getId());
                houseService.updateById(house);
            }

            OwnerRelation ownerRelation = new OwnerRelation();
            ownerRelation.setOwnerId(houseVo.getOwnerId());
            ownerRelation.setUsername(houseVo.getName());
            ownerRelation.setGender(houseVo.getGender());
            ownerRelation.setPhone(houseVo.getPhone());
            if (houseVo.getType() == 0) {
                ownerRelation.setRelation("家属");
                boolean b = ownerRelationService.save(ownerRelation);
                if (!b) throw new ServerException("服务器异常");
            }else if (houseVo.getType() == 1){
                ownerRelation.setRelation("租户");
                boolean b = ownerRelationService.save(ownerRelation);
                if (!b) throw new ServerException("服务器异常");
            }
        }
        return Result.ok();
    }

    /**
        家庭成员
    */
    @GetMapping("/familyList")
    public Result familyList(@RequestParam(value = "type",required = false) Integer type,
                             @RequestParam(value = "owner_id", required = false) Integer ownerId) {
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        if (ownerId != null) {
            Community community = communityService.getById(communityId);
            List<Object> family = new ArrayList<>();
            HouseInfo houseInfo = new HouseInfo();
            houseInfo.setCommunityId(Integer.valueOf(communityId));
            houseInfo.setCommunityName(community.getName());
            List<BuildingInfo> buildings = new ArrayList<>();

            QueryWrapper<Building> wp = new QueryWrapper<>();
            wp.eq("community_id", communityId);
            List<Building> builds = buildingService.list(wp);
            for (Building building : builds) {
                BuildingInfo buildingInfo = new BuildingInfo();
                buildingInfo.setBuildingId(building.getId());
                buildingInfo.setBuildingName(building.getBuildingName());

                QueryWrapper<House> hwp = new QueryWrapper<>();
                hwp.eq("community_id", communityId);
                hwp.eq("owner_id", ownerId);
                hwp.eq("building_id", building.getId());
                List<House> houses = houseService.list(hwp);
                List<RoomInfo> rooms = new ArrayList<>();
                for (House house : houses) {
                    RoomInfo roomInfo = new RoomInfo();
                    roomInfo.setRoomId(house.getId());
                    roomInfo.setRoomName(house.getRoomNo());
                    roomInfo.setUnit(String.valueOf(house.getUnit()));
                    rooms.add(roomInfo);
                }
                buildingInfo.setRooms(rooms);
                buildings.add(buildingInfo);

            }
            houseInfo.setBuildings(buildings);
            //已注册
            if (type == 0) {
                QueryWrapper<OwnerRelation> owp = new QueryWrapper<>();
                owp.eq("owner_id", ownerId);
                OwnerRelation ownerRelation = ownerRelationService.getOne(owp);
                if (ownerRelation != null)
                    if (ownerRelation.getOwnerId() == ownerId)
                        family.add(ownerRelation);
            }else if (type == 1){
                QueryWrapper<OwnerRelation> owp = new QueryWrapper<>();
                owp.eq("owner_id", ownerId);
                OwnerRelation ownerRelation = ownerRelationService.getOne(owp);
                if (ownerRelation != null)
                    if (ownerRelation.getOwnerId() == ownerId)
                        family.add(ownerRelation);
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("houseInfo",houseInfo);
            map.put("family", family);
            return Result.ok(map);
        }
        return Result.error();
    }

    @DeleteMapping("/deleteMembers")
    public Result<Object> deleteMembers(@RequestBody List<Integer> id) {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (id.size() == 1) {
            ownerRelationService.removeById(id.get(0));
        }else {
            ownerRelationService.removeBatchByIds(id);
        }
        return Result.ok();
    }
}
