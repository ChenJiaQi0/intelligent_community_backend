package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;
import com.soft2242.system.service.CommunityService;
import com.soft2242.system.service.HouseService;
import com.soft2242.system.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 切换小区
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class CommunityController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private HouseService houseService;
    @Resource
    private CommunityService communityService;
    @Resource
    private JwtUtil jwtUtil;

    @GetMapping("/community")
    public Result community(@RequestParam(value = "type", required = false) Integer type,
                            @RequestParam(value = "owner_id", required = false) String ownerId,
                            @RequestParam(value = "property_id", required = false) String propertyId){
        //判断是否登录
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        Map<String, Object> map = new HashMap<>();
        List<Community> communityList = new ArrayList<>();

        if (type == 0){
            //业主身份
            if (ownerId != null){
                QueryWrapper<House> wp = new QueryWrapper<>();
                wp.eq("owner_id", ownerId);
                wp.eq("is_default", 1);
                //获取业主房屋
                House defaultHouse = houseService.getOne(wp);
                if (defaultHouse == null)
                {
                    List<Community> list = communityService.list();
                    map.put("community_list", list);
                }
                else
                {
                    Community defaultCommunity = communityService.getById(defaultHouse.getCommunityId());
                    map.put("default_community", defaultCommunity);
                    communityList.add(defaultCommunity);
                    QueryWrapper<Community> wp2 = new QueryWrapper<>();
                    wp2.notIn("id", defaultHouse.getCommunityId());
                    List<Community> communities = communityService.list(wp2);
                    communityList.addAll(communities);
                    map.put("community_list", communityList);
                }
            } else {
                List<Community> list = communityService.list();
                map.put("community_list", list);
            }
        }else if(propertyId != null && type == 1){
            //物业身份
            map.put("default_community", null);
            List<Community> communities = communityService.list();
            map.put("community_list", communities);
        }
        return Result.ok(map);
    }
}
