package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;
 
import com.soft2242.system.util.JwtUtil;
import com.soft2242.system.service.CommunityService;
import com.soft2242.system.service.EquipmentService;
import com.soft2242.system.service.VisitorService;
import com.soft2242.system.service.VistorLogService;
import com.soft2242.system.vo.CodeListVo;
import com.soft2242.system.vo.VLogVo;
import com.soft2242.system.vo.VisitorVo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 访客邀请
 */
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class VisitorController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private EquipmentService equipmentService;
    @Resource
    private VisitorService visitorService;
    @Resource
    private VistorLogService vistorLogService;
    @Resource
    private CommunityService communityService;

    /**
     * 访客邀请
     */
    @PostMapping("/vister")
    public Result vister(@RequestBody VisitorVo vo){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (vo.getOwnerId() == null && vo.getPropertyId() == null) return Result.ok();


        String communityId = request.getHeader("community_id");

        List<Equipment> equips = vo.getAuthorizedDevice();
        UUID uuid = UUID.randomUUID();
        for (Equipment equip : equips) {
            Visitor visitor = new Visitor();
            visitor.setUuid(uuid.toString());
            visitor.setCommunityId(Integer.valueOf(communityId));
            if (vo.getOwnerId() == null) visitor.setAuthorizer(vo.getPropertyId());
            else visitor.setAuthorizer(vo.getOwnerId());

            visitor.setEffectiveTime(vo.getAuthorizedTime()/1000/60/60);
            visitor.setAuthorizedDevice(equip.getEquipName());
            visitor.setIsEffective(1);
            boolean flag = visitorService.save(visitor);
            if (!flag) throw new ServerException("服务器异常");
        }
        return Result.ok();
    }

    /**
     *  获取邀请码记录列表
     */
    @GetMapping("/getCodeList")
    public Result getCodeList(@RequestParam("owner_id") Integer ownerId){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) throw new ServerException("未查到账号的邀请信息");

        QueryWrapper<Visitor> vwp = new QueryWrapper<>();
        vwp.eq("authorizer", ownerId);

        List<Integer> communityIds = new ArrayList<>();
        List<Visitor> visitors = visitorService.list(vwp);
//        log.info(visitors.toString());

        for (Visitor visitor : visitors) {
            if (!communityIds.contains(visitor.getCommunityId())) communityIds.add(visitor.getCommunityId());
        }
        List<CodeListVo> codeListVos = new ArrayList<>();

        for (Integer communityId : communityIds) {
            List<String> uuids = visitorService.selectByUUID(ownerId, communityId);

            for (String uuid : uuids) {
                CodeListVo codeListVo = new CodeListVo();
                codeListVo.setUuid(uuid);
                Community community = communityService.getById(communityId);
                codeListVo.setCodeName(community.getName());
                List<String> devices = new ArrayList<>();
                boolean flag = true;
                for (Visitor visitor : visitors) {
                    if (uuid.equals(visitor.getUuid())){
                        devices.add(visitor.getAuthorizedDevice());
                        if (visitor.getIsEffective() == 0) flag = false;
                        codeListVo.setEffectiveTime(String.valueOf(visitor.getEffectiveTime()));
                    }
                }
                codeListVo.setDevices(devices);
                if (flag) codeListVo.setFailure(1);
                codeListVos.add(codeListVo);
            }
        }

        return Result.ok(codeListVos);
    }

    /**
     * 获取开门记录
     */
    @GetMapping("/getRecord")
    public Result getRecord(@RequestParam("owner_id") Integer ownerId){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) throw new ServerException("未查到账号的访客信息");

        QueryWrapper<VistorLog> vwp = new QueryWrapper<>();
        vwp.eq("owner_id", ownerId);
        List<VistorLog> vlogs = vistorLogService.list(vwp);

        List<VLogVo> lists = new ArrayList<>();

        for (VistorLog vlog : vlogs) {
            VLogVo vLogVo = new VLogVo();
            Equipment equip = equipmentService.getById(vlog.getEquipId());
            vLogVo.setDevice(equip.getEquipName());
            vLogVo.setOpen_time(vlog.getAuthorizedTime());
            lists.add(vLogVo);
        }
        return Result.ok(lists);
    }

    /**
     * 邀请码无效
     */
    @PostMapping("/updateCode")
    public Result updateCode(@RequestBody Visitor uuid){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        QueryWrapper<Visitor> vwp = new QueryWrapper<>();
        vwp.eq("uuid", uuid.getUuid());

        List<Visitor> list = visitorService.list(vwp);
        log.info(list.toString());
        for (Visitor visitor : list) {
            visitor.setIsEffective(0);
            boolean flag = visitorService.updateById(visitor);
            if (!flag) throw new ServerException("服务器异常");
        }

        return Result.ok();
    }

    @PostMapping("/openDoor")
    public Result openDoor(@RequestBody VisitorVo vo){
        System.out.println(vo);
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        if (vo.getType() == 0 && vo.getOwnerId() != null){
            List<Integer> doors = vo.getDoors();
            for (Integer equipId : doors) {
                VistorLog log = new VistorLog();
                log.setOwnerId(vo.getOwnerId());
                log.setAuthorizedTime(new Date());
                log.setEquipId(equipId);
                System.out.println(log);
                boolean flag = vistorLogService.save(log);
                if (!flag) throw new ServerException("服务器异常");
            }
        }
        return Result.ok();
    }
}
