package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Community;
import com.soft2242.system.entity.Property;
import com.soft2242.system.entity.PropertyCompany;
import com.soft2242.system.service.CommunityService;
import com.soft2242.system.service.PropertyCompanyService;
import com.soft2242.system.service.PropertyService;
import com.soft2242.system.util.JwtUtil;
import com.soft2242.system.vo.CompanyVo;
import com.soft2242.system.vo.PropertyVo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/***
 * @description:
 * @author: yk
 **/

@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class ConnectionController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommunityService communityService;
    @Resource
    private PropertyService propertyService;
    @Resource
    private PropertyCompanyService propertyCompanyService;
    /**
        联系物业1
     */
    @GetMapping("/getProperty")
    public Result getProperty(@RequestParam(value = "owner_id", required = false) Integer ownerId) {
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) {
            throw new ServerException("没有联系物业的小区信息");
        }
        if (ownerId != null) {
            QueryWrapper<Property> wp = new QueryWrapper<>();
            wp.eq("community_id",communityId);
            List<PropertyVo> houseAndProperty = new ArrayList<>();
            List<Property> properties = propertyService.list(wp);
            for (Property property : properties) {
                PropertyVo propertyVo = new PropertyVo();
                propertyVo.setTelephone(property.getTelephone());

                QueryWrapper<PropertyCompany> pwq = new QueryWrapper<>();
                pwq.eq("id", property.getCompanyId());
                List<PropertyCompany> propertyCompanies = propertyCompanyService.list(pwq);
                List<CompanyVo> companyVos = new ArrayList<>();
                for (PropertyCompany propertyCompany : propertyCompanies) {
                    CompanyVo companyVo = new CompanyVo();
                    companyVo.setCompanyName(propertyCompany.getCompanyName());
                    companyVos.add(companyVo);
                }
                propertyVo.setCompanyVos(companyVos);

                QueryWrapper<Community> wrapper = new QueryWrapper<>();
                wrapper.eq("id", property.getCommunityId());
//                wrapper.eq("owner_id", ownerId);
                Community community = communityService.getOne(wrapper);
                if (community != null) {
                    propertyVo.setCommunity(community);
                }
                houseAndProperty.add(propertyVo);

            }

            return Result.ok(houseAndProperty);
        }
        return Result.error();
    }
    @GetMapping("/getPropertyCompany")
    public Result getPropertyCompany() {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        List<PropertyCompany> list = propertyCompanyService.list();
        return Result.ok(list);
    }
}
