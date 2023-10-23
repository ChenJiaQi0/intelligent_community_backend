package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;
import com.soft2242.system.service.*;
import com.soft2242.system.util.JwtUtil;
import com.soft2242.system.vo.PatrolVo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 物业端 今日巡更
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class PatrolController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private PatrolProjectService project;
    @Resource
    private RoleProjectService roleProjectService;
    @Resource
    private PatrolRoleService patrolRoleService;
    @Resource
    private PropertyService propertyService;
    @Resource
    private PatrolTypeService typeService;
    @Resource
    private CommunityService communityService;
    @Resource
    private PatrolLogService patrolLogService;
    @Resource
    private PatrolPointService patrolPointService;
    @Resource
    private PatrolPlanService patrolPlanService;

    /**
     * 获取被分配的巡更计划及查看详情
     */
    @GetMapping("/patrol/getProject")
    public Result getProject(@RequestParam(value = "role_id", required = false) Integer roleId){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        String communityId = request.getHeader("community_id");
        Community community = communityService.getById(communityId);

        QueryWrapper<PatrolPlanEntity> wp = new QueryWrapper<>();
        wp.eq("community_id", communityId).eq("state", 0);
        List<PatrolPlanEntity> plans = patrolPlanService.list(wp);
        log.info(plans.toString());
        if (plans.size() == 0) return Result.ok();

        if (roleId != null) {
            PatrolVo vo = new PatrolVo();
            PatrolRole role = patrolRoleService.getById(roleId);
            vo.setInspectionRouteName(role.getInspectionRouteName());
            vo.setId(roleId);
            vo.setStartTime(role.getInspectionStartTime());
            vo.setEndTime(role.getInspectionEndTime());
            vo.setCommunityName(community.getName());
            PatrolType type = typeService.getById(role.getPatrolType());
            vo.setPatrolType(type.getInspectionClassificationName());

            QueryWrapper<PatrolLog> wp2 = new QueryWrapper<>();
            wp2.eq("inspection_points_id", roleId);
            PatrolLog Plog = patrolLogService.getOne(wp2);

            if (Plog != null){
                vo.setResult(Plog.getInspectionResult());
                if (Plog.getInspectionPhotos() != null){
                    String[] photos = Plog.getInspectionPhotos().split(",");
                    vo.setPhotos(photos);
                }
                vo.setUpdateTime(Plog.getCreateTime());
            }
            return Result.ok(vo);
        }
        List<PatrolVo> list = new ArrayList<>();

        for (PatrolPlanEntity plan : plans) {
            PatrolVo vo = new PatrolVo();
            vo.setCommunityName(community.getName());

            QueryWrapper<PatrolRole> rwp = new QueryWrapper<>();
            rwp.eq("inspection_route_name", plan.getPatrolProject()).eq("inspection_route_state", 1);
            PatrolRole role = patrolRoleService.getOne(rwp);
            if (role != null) {
                vo.setInspectionRouteName(role.getInspectionRouteName());
                PatrolType type = typeService.getById(role.getPatrolType());
                vo.setPatrolType(type.getInspectionClassificationName());
                vo.setStartTime(role.getInspectionStartTime());
                vo.setEndTime(role.getInspectionEndTime());

                Integer fromId = role.getFromPatrolPoint();
                Integer endId = role.getToPatrolPoint();
                if (fromId == endId) {
                    PatrolPoint point = patrolPointService.getById(fromId);
                    if (point.getStatus() == 0) {
                        vo.setState(0);
                    } else {
                        vo.setState(1);
                    }
                } else {
                    PatrolPoint from = patrolPointService.getById(fromId);
                    PatrolPoint end = patrolPointService.getById(endId);
                    if (from.getStatus() == 0 && end.getStatus() == 0) {
                        vo.setState(0);
                    } else {
                        vo.setState(1);
                    }
                }
                vo.setId(role.getId());

                list.add(vo);
            }
        }

//        if (roleId != null){    //巡更详情
//            PatrolVo vo = new PatrolVo();
//            vo.setId(roleId);
//            Property property = propertyService.getById(propertyId);
//            vo.setProjectExecutor(property.getUsername());
//            vo.setPhone(property.getPhone());
//
//            PatrolRole role = patrolRoleService.getById(roleId);
//            vo.setInspectionRouteName(role.getInspectionRouteName());
//            vo.setStartTime(role.getInspectionStartTime());
//            vo.setEndTime(role.getInspectionEndTime());
//
//            PatrolType type = typeService.getById(role.getPatrolType());
//            vo.setPatrolType(type.getInspectionClassificationName());
//
//            QueryWrapper<PatrolLog> wp1 = new QueryWrapper<>();
//            wp1.eq("inspector_id", propertyId).eq("inspection_points_id", role.getFromPatrolPoint());
//            PatrolLog log = patrolLogService.getOne(wp1);
//
//            if (log != null){
//                vo.setResult(log.getInspectionResult());
//                if (log.getInspectionPhotos() != null){
//                    String[] photos = log.getInspectionPhotos().split(",");
//                    vo.setPhotos(photos);
//                }
//                vo.setUpdateTime(log.getCreateTime());
//            }
//            return Result.ok(vo);
//        }
//
//        QueryWrapper<PatrolProject> pwp = new QueryWrapper<>();
//        pwp.eq("community_id", communityId).eq("project_executor", propertyId).eq("state", 1);
//        List<PatrolProject> projects = project.list(pwp);
//        if (projects.size() == 0) return Result.ok();
//
//        List<Integer> projectIds = new ArrayList<>();
//        for (PatrolProject patrolProject : projects) {
//            projectIds.add(patrolProject.getId());
//        }
//        QueryWrapper<RoleProject> rwp = new QueryWrapper<>();
//        rwp.in("project_id", projectIds);
//        List<RoleProject> roleProjectList = roleProjectService.list(rwp);
//        List<Integer> roleIds = new ArrayList<>();
//        for (RoleProject roleProject : roleProjectList) {
//            roleIds.add(roleProject.getRoleId());
//        }
//
//        QueryWrapper<PatrolRole> prwp = new QueryWrapper<>();
//        prwp.eq("community_id", communityId).in("id", roleIds);
//        List<PatrolRole> patrolRoles = patrolRoleService.list(prwp);
//        if (patrolRoles.size() == 0) return Result.ok();
//
//        List<PatrolVo> list = new ArrayList<>();
//        for (PatrolRole patrolRole : patrolRoles) {
//            PatrolVo vo = new PatrolVo();
////            vo.setState(patrolRole.getInspectionRouteState());
//            PatrolPoint from = patrolPointService.getById(patrolRole.getFromPatrolPoint());
//            PatrolPoint to = patrolPointService.getById(patrolRole.getToPatrolPoint());
//            if (from.getStatus() == 1 && to.getStatus() == 1){
//                vo.setState(1);
//            }else {
//                vo.setState(0);
//            }
//
//            vo.setId(patrolRole.getId());
//            vo.setCommunityName(community.getName());
//            vo.setInspectionRouteName(patrolRole.getInspectionRouteName());
//
//            QueryWrapper<PatrolType> wp1 = new QueryWrapper<>();
//            wp1.eq("community_id", communityId).eq("id", patrolRole.getPatrolType());
//            PatrolType type = typeService.getOne(wp1);
//            vo.setPatrolType(type.getInspectionClassificationName());
//
//            Property property = propertyService.getById(propertyId);
//            vo.setProjectExecutor(property.getUsername());
//
//            vo.setStartTime(patrolRole.getInspectionStartTime());
//            vo.setEndTime(patrolRole.getInspectionEndTime());
//
//            list.add(vo);
//        }



        return Result.ok(list);
    }

    /**
     * 去巡更
     */
    @PostMapping("/patrol/addLog")
    public Result addLog(@RequestBody PatrolVo patrolVo){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        PatrolLog patrolLog = new PatrolLog();
        if (patrolVo.getResult() !=null) patrolLog.setInspectionResult(patrolVo.getResult());

        if (patrolVo.getPhotos() != null){
            if (patrolVo.getPhotos().length > 0){
                String photos = "";
                for (int i=0;i<patrolVo.getPhotos().length;i++){
                    if (i == patrolVo.getPhotos().length-1){
                        photos+= patrolVo.getPhotos()[i];
                    }else{
                        photos+= patrolVo.getPhotos()[i] + ",";
                    }
                }
                patrolLog.setInspectionPhotos(photos);
            }
        }
//        QueryWrapper<Property> wp1 = new QueryWrapper<>();
//        wp1.eq("username", patrolVo.getProjectExecutor()).eq("phone", patrolVo.getPhone());
//        Property property = propertyService.getOne(wp1);
//        patrolLog.setInspectorId(property.getId());
        PatrolRole role = patrolRoleService.getById(patrolVo.getId());
        patrolLog.setPatrolType(role.getPatrolType());
        patrolLog.setInspectionPointsId(patrolVo.getId());

        log.info(patrolLog.toString());
        boolean flag = patrolLogService.save(patrolLog);

        PatrolPoint fromPoint = patrolPointService.getById(role.getFromPatrolPoint());
        fromPoint.setStatus(1);
        boolean flag1 = patrolPointService.updateById(fromPoint);

        PatrolPoint toPoint = patrolPointService.getById(role.getToPatrolPoint());
        toPoint.setStatus(1);
        boolean flag2 = patrolPointService.updateById(toPoint);
        if (!flag && !flag2 && !flag1) throw new ServerException("服务器异常");

        return Result.ok();
    }

//    @GetMapping("/patrol/countProject")
//    public Result<PatrolRole> countProject(@RequestParam("property_id") Integer propertyId){
//        String token = request.getHeader("token");
//        Claims claims = jwtUtil.parseJWT(token);
//        if (claims.isEmpty()) throw new ServerException("请先登录");
//        String communityId = request.getHeader("community_id");
//
//        QueryWrapper<PatrolRole> wp = new QueryWrapper<>();
//        wp.eq("")
//    }
}
