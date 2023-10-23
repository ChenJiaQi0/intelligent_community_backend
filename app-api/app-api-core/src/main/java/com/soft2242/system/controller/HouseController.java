package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.List;

/***
 * 我的房屋功能
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class HouseController {
    @Resource
    private AccountService accountService;
    @Resource
    private OwnerService ownerService;
    @Resource
    private HouseService houseService;
    @Resource
    private CommunityService communityService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private OwnerRelationService ownerRelationService;

    /**
     *  房屋信息
     */
    @GetMapping("/HouseList")
    public Result HouseList(@RequestParam(value = "type", required = false) Integer type,
                            @RequestParam(value = "owner_id", required = false) String ownerId){
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        //首页   判断是否是游客
        if (ownerId == null && type == null){
            return Result.ok();
        }

        //首页   判断是否是业主  type=2:请求该业主的当前小区的所有房屋
        if (ownerId !=null && type == 2){
            Community community = communityService.getById(communityId);
            if (community == null) return Result.error("该小区没有业主房屋信息");

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
                    roomInfo.setType(house.getOwnerUserType());
                    rooms.add(roomInfo);
                }

                buildingInfo.setRooms(rooms);
                buildings.add(buildingInfo);
            }
            houseInfo.setBuildings(buildings);
            return Result.ok(houseInfo);
        }

        //我是业主 添加房屋 type=0:请求所有未绑定业主的房屋
        if (type == 0){
            List<Community> communities = communityService.list();
            if (communities.size() == 0) throw new ServerException("服务器异常");

            List<HouseInfo> houseInfos = new ArrayList<>();

            for (Community community : communities) {
                HouseInfo houseInfo = new HouseInfo();
                houseInfo.setCommunityId(community.getId());
                houseInfo.setCommunityName(community.getName());

                List<BuildingInfo> buildings = new ArrayList<>();
                QueryWrapper<Building> bwp = new QueryWrapper<>();
                bwp.eq("community_id", community.getId());
                List<Building> builds = buildingService.list(bwp);

                for (Building build : builds) {
                    BuildingInfo buildingInfo = new BuildingInfo();
                    buildingInfo.setBuildingId(build.getId());
                    buildingInfo.setBuildingName(build.getBuildingName());

                    QueryWrapper<House> hwp = new QueryWrapper<>();
                    hwp.eq("community_id", community.getId());
                    hwp.isNull("owner_id");

                    hwp.eq("building_id", build.getId());
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
                houseInfos.add(houseInfo);
            }
            return Result.ok(houseInfos);
        }

        //我是住户 入住房屋  type=1:请求所有绑定的业主的房屋
        if (type == 1){
            List<Community> communities = communityService.list();
            if (communities.size() == 0) throw new ServerException("服务器异常");

            List<HouseInfo> houseInfos = new ArrayList<>();

            for (Community community : communities) {
                HouseInfo houseInfo = new HouseInfo();
                houseInfo.setCommunityId(community.getId());
                houseInfo.setCommunityName(community.getName());

                List<BuildingInfo> buildings = new ArrayList<>();
                QueryWrapper<Building> bwp = new QueryWrapper<>();
                bwp.eq("community_id", community.getId());
                List<Building> builds = buildingService.list(bwp);

                for (Building build : builds) {
                    BuildingInfo buildingInfo = new BuildingInfo();
                    buildingInfo.setBuildingId(build.getId());
                    buildingInfo.setBuildingName(build.getBuildingName());

                    QueryWrapper<House> hwp = new QueryWrapper<>();
                    hwp.eq("community_id", community.getId());
                    hwp.isNotNull("owner_id");
                    hwp.isNull("user_id");
                    hwp.eq("building_id", build.getId());
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
                houseInfos.add(houseInfo);
            }
            return Result.ok(houseInfos);
        }
        return Result.ok();
    }

    /**
     *  添加房屋
     */
    @PostMapping("/AddHouse")
    public Result AddHouse(@RequestBody AddHouseVo houseVo){
        log.info(houseVo.toString());
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        //业主添加
        if (houseVo.getStatus() == 0){
            //游客添加房屋 并创建他对应业主信息
            if (houseVo.getOwnerId() == null){
                //为游客创建业主
                Owner userOwner = new Owner();
                userOwner.setNickname(houseVo.getName());
                userOwner.setUsername(houseVo.getName());
                userOwner.setGender(houseVo.getGender());
                userOwner.setPhone(houseVo.getPhone());
                userOwner.setIdCard(houseVo.getIdentityCard());
                userOwner.setIsOwner(1);
                boolean flag = ownerService.save(userOwner);
                if (!flag) throw new ServerException("服务器异常");

                //查出游客账号
                QueryWrapper<Account> awp = new QueryWrapper<>();
                awp.eq("account", houseVo.getPhone());
                Account user = accountService.getOne(awp);
                user.setIdentity(2);
                user.setOwnerId(userOwner.getId());
                accountService.updateById(user);

                QueryWrapper<House> hwp = new QueryWrapper<>();
                hwp.eq("community_id", houseVo.getCommunityId());
                hwp.eq("building_id", houseVo.getBuildingId());
                hwp.eq("unit", houseVo.getUnit());
                hwp.eq("room_no", houseVo.getRoomNo());
                House house = houseService.getOne(hwp);
                house.setOwnerUserType("业主");
                house.setOwnerId(userOwner.getId());
                houseService.updateById(house);
            }else{  //业主添加房屋
                Owner owner = new Owner();
                owner.setId(houseVo.getOwnerId());
                owner.setPhone(houseVo.getPhone());
                owner.setGender(houseVo.getGender());
                owner.setIdCard(houseVo.getIdentityCard());
                owner.setUsername(houseVo.getName());
                owner.setNickname(houseVo.getName());
                boolean flag = ownerService.updateById(owner);
                if (!flag) throw new ServerException("服务器异常");

                QueryWrapper<House> hwp = new QueryWrapper<>();
                hwp.eq("community_id", houseVo.getCommunityId());
                hwp.eq("building_id", houseVo.getBuildingId());
                hwp.eq("unit", houseVo.getUnit());
                hwp.eq("room_no", houseVo.getRoomNo());
                House house = houseService.getOne(hwp);
                house.setOwnerUserType("业主");
                house.setOwnerId(houseVo.getOwnerId());
                boolean flag2 = houseService.updateById(house);
                if (!flag2) throw new ServerException("服务器异常");

            }
            return Result.ok();
        }else{  //住户添加
            //游客的住户添加
            if (houseVo.getOwnerId() == null){
                //为游客创建业主
                Owner userOwner = new Owner();
                userOwner.setNickname(houseVo.getName());
                userOwner.setUsername(houseVo.getName());
                userOwner.setGender(houseVo.getGender());
                userOwner.setPhone(houseVo.getPhone());
                userOwner.setIdCard(houseVo.getIdentityCard());
                userOwner.setIsOwner(0);
                boolean flag = ownerService.save(userOwner);
                if (!flag) throw new ServerException("服务器异常");

                //查出游客账号
                QueryWrapper<Account> awp = new QueryWrapper<>();
                awp.eq("account", houseVo.getPhone());
                Account user = accountService.getOne(awp);
                user.setOwnerId(userOwner.getId());
                accountService.updateById(user);

                //住户
                QueryWrapper<House> hwp = new QueryWrapper<>();
                hwp.eq("community_id", houseVo.getCommunityId());
                hwp.eq("building_id", houseVo.getBuildingId());
                hwp.eq("unit", houseVo.getUnit());
                hwp.eq("room_no", houseVo.getRoomNo());
                House house = houseService.getOne(hwp);
                house.setUserId(userOwner.getId());

                OwnerRelation relation = new OwnerRelation();
                relation.setOwnerId(house.getOwnerId());
                relation.setUsername(houseVo.getName());
                relation.setGender(houseVo.getGender());
                relation.setPhone(houseVo.getPhone());
                if (houseVo.getType() == 0){    //0:是家属
                    house.setOwnerUserType("家属");
                    relation.setRelation("家属");
                    boolean flag2 = ownerRelationService.save(relation);
                    if (!flag2) throw new ServerException("服务器异常");
                }else{  //租户
                    relation.setRelation("租户");
                    house.setOwnerUserType("租户");
                    boolean flag2 = ownerRelationService.save(relation);
                    if (!flag2) throw new ServerException("服务器异常");
                }
                houseService.updateById(house);
            }else{  //业主入住房屋
                Owner owner = new Owner();
                owner.setId(houseVo.getOwnerId());
                owner.setPhone(houseVo.getPhone());
                owner.setGender(houseVo.getGender());
                owner.setIdCard(houseVo.getIdentityCard());
                owner.setUsername(houseVo.getName());
                owner.setNickname(houseVo.getName());
                boolean flag = ownerService.updateById(owner);
                if (!flag) throw new ServerException("服务器异常");

                QueryWrapper<House> hwp = new QueryWrapper<>();
                hwp.eq("community_id", houseVo.getCommunityId());
                hwp.eq("building_id", houseVo.getBuildingId());
                hwp.eq("unit", houseVo.getUnit());
                hwp.eq("room_no", houseVo.getRoomNo());
                House house = houseService.getOne(hwp);
                house.setUserId(houseVo.getOwnerId());

                OwnerRelation relation = new OwnerRelation();
                relation.setOwnerId(house.getOwnerId());
                relation.setUsername(houseVo.getName());
                relation.setGender(houseVo.getGender());
                relation.setPhone(houseVo.getPhone());
                if (houseVo.getType() == 0){    //0:是家属
                    relation.setRelation("家属");
                    house.setOwnerUserType("家属");
                    boolean flag3 = ownerRelationService.save(relation);
                    if (!flag3) throw new ServerException("服务器异常");
                }else{  //租户
                    relation.setRelation("租户");
                    house.setOwnerUserType("租户");
                    boolean flag4 = ownerRelationService.save(relation);
                    if (!flag4) throw new ServerException("服务器异常");
                }
                boolean flag2 = houseService.updateById(house);
                if (!flag2) throw new ServerException("服务器异常");
            }
            return Result.ok();
        }
    }

    /**
     * 获取业主手机号
     */
    @GetMapping("/getOwnerPhone")
    public Result<String> getOwnerPhone(@RequestParam("roomId") Integer roomId){
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        House house = houseService.getById(roomId);
        QueryWrapper<Owner> owp = new QueryWrapper<>();
        owp.eq("id", house.getOwnerId());
        Owner owner = ownerService.getOne(owp);
        if (owner == null) throw new ServerException("服务器异常");

        return Result.ok(owner.getPhone());
    }

    /**
     * 删除房屋
     */
    @DeleteMapping("/house/{houseId}/{ownerId}")
    public Result deleteHouse(@PathVariable("houseId") Integer houseId,
                              @PathVariable("ownerId") Integer ownerId){

        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        House house = houseService.getById(houseId);
        Owner owner = ownerService.getById(ownerId);

        UpdateWrapper<House> up = new UpdateWrapper<>();
        Integer isOwner = owner.getIsOwner();
        if (isOwner == 1){  //是业主
            house.setOwnerId(null);
            up.eq("id", houseId).set("owner_id", null);
        }else{
            house.setUserId(null);
            up.eq("id", houseId).set("user_id", null);

        }

        boolean flag = houseService.update(up);
        if (!flag) throw new ServerException("服务器异常");

        return Result.ok();
    }




}
