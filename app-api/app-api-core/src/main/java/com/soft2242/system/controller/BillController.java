package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.*;
import com.soft2242.system.service.*;
import com.soft2242.system.util.JwtUtil;
import com.soft2242.system.service.*;
import com.soft2242.system.vo.BillByYearVo;
import com.soft2242.system.vo.BillVo;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/***
 * 生活缴费
 **/
@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class BillController {
    @Resource
    private HttpServletRequest request;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HouseService houseService;
    @Resource
    private BillService billService;
    @Resource
    private BuildingService buildingService;
    @Resource
    private CommunityService communityService;
    @Resource
    private ProjectService projectService;

    /**
     * 获取用户所有账单
     *
     */
    @GetMapping("/getBills")
    public Result getBills(@RequestParam(value = "owner_id", required = false) Integer ownerId,
                           @RequestParam(value = "bill_id", required = false) Integer billId){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId == null) throw new ServerException("游客看不到账单信息");

        //查询该业主所在小区的所有账单信息
        String communityId = request.getHeader("community_id");
        List<BillVo> bills = new ArrayList<>();
        //获取业主对应房屋信息
        QueryWrapper<House> wp = new QueryWrapper<>();
        wp.eq("community_id", communityId);
        wp.eq("owner_id", ownerId);

        //id不为null就是账单详情
        if (billId != null){
            Bill bill = billService.getById(billId);
            BillVo vo = new BillVo();
            vo.setBillId(bill.getId());
            House house = houseService.getById(bill.getHouseId());
            QueryWrapper<Building> wp1 = new QueryWrapper<>();
            wp1.eq("id", house.getBuildingId());
            Building build = buildingService.getOne(wp1);
            Community community = communityService.getById(communityId);
            vo.setHouse(community.getName() + build.getBuildingName() + house.getUnit() + "-" + house.getRoomNo());
            vo.setHouseArea(house.getUsageArea() + "m²");
            vo.setStartTime(bill.getCreateTime());
            vo.setEndTime(bill.getDeadline());

            Project project = projectService.getById(bill.getProject());
            vo.setProjectName(project.getProjectName());
            vo.setIcon(project.getIcon());
            vo.setPrice(project.getPrice() + project.getUnit() + "/" + project.getPer() + "/每月");

            if (bill.getPaid() == 1) {
                vo.setPaidTime(bill.getUpdateTime());
            }

            vo.setTotal(project.getPrice() * house.getUsageArea());

            return Result.ok(vo);
        }

        List<House> houses = houseService.list(wp);
        if (houses.size() == 0) return Result.ok();
        for (House house : houses) {
            QueryWrapper<Bill> wp2 = new QueryWrapper<>();
            wp2.in("house_id", house.getId());
            List<Bill> billList = billService.list(wp2);
            for (Bill bill : billList) {
                BillVo vo = new BillVo();
                vo.setBillId(bill.getId());
                QueryWrapper<Building> wp1 = new QueryWrapper<>();
                wp1.eq("id", house.getBuildingId());
                Building build = buildingService.getOne(wp1);
                Community community = communityService.getById(communityId);
                vo.setHouse(community.getName() + build.getBuildingName() + house.getUnit() + "-" + house.getRoomNo());
                vo.setHouseArea(house.getUsageArea() + "m²");
                vo.setStartTime(bill.getCreateTime());
                vo.setEndTime(bill.getDeadline());

                Project project = projectService.getById(bill.getProject());
                vo.setProjectName(project.getProjectName());
                vo.setIcon(project.getIcon());
                vo.setPrice(project.getPrice() + project.getUnit() + "/" + project.getPer() + "/每月");

                if (bill.getPaid() == 1) {
                    vo.setPaidTime(bill.getUpdateTime());
                }

                vo.setTotal(project.getPrice() * house.getUsageArea());
                bills.add(vo);
            }
        }

        return Result.ok(bills);
    }

    @PostMapping("/updateBill")
    public Result updateBill(@RequestBody Bill bill){
        Bill update = billService.getById(bill.getId());
        if (update == null) throw new ServerException("查无此账单");

        update.setPaid(1);
        boolean flag = billService.updateById(update);
        if (!flag) throw new ServerException("更新账单失败");

        return Result.ok();
    }

    @GetMapping("/listByYear")
    public Result listByYear(@RequestParam(value = "owner_id", required = false) Integer ownerId){
        String token = request.getHeader("token");
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        //查询该业主所在小区的所有账单信息
        String communityId = request.getHeader("community_id");

        //获取业主对应房屋信息
        QueryWrapper<House> wp = new QueryWrapper<>();
        wp.eq("community_id", communityId);
        wp.eq("owner_id", ownerId);
        List<House> houses = houseService.list(wp);
        if (houses.size() == 0) return Result.ok();
        List<BillByYearVo> list = new ArrayList<>();

        for (House house : houses) {
            QueryWrapper<Bill> wp2 = new QueryWrapper<>();
            wp2.in("house_id", house.getId());
            wp2.orderByDesc("year");
            List<Bill> billList = billService.list(wp2);
//            log.info(billList.toString());

            BillByYearVo billByYearVo = new BillByYearVo();
            billByYearVo.setYear(billList.get(0).getYear());
            List<BillVo> billVos = new ArrayList<>();
            billByYearVo.setPayList(billVos);
            for (int i = 0; i < billList.size(); i++) {
                Bill bill = billList.get(i);

                if (bill.getPaid() == 1){
                    BillVo vo = new BillVo();
                    vo.setBillId(bill.getId());
                    QueryWrapper<Building> wp1 = new QueryWrapper<>();
                    wp1.eq("id", house.getBuildingId());
                    Building build = buildingService.getOne(wp1);
                    Community community = communityService.getById(communityId);
                    vo.setHouse(community.getName() + build.getBuildingName() + house.getUnit() + "-" + house.getRoomNo());
                    vo.setHouseArea(house.getUsageArea() + "m²");
                    vo.setStartTime(bill.getCreateTime());
                    vo.setEndTime(bill.getDeadline());

                    Project project = projectService.getById(bill.getProject());
                    vo.setProjectName(project.getProjectName());
                    vo.setIcon(project.getIcon());
                    vo.setPrice(project.getPrice() + project.getUnit() + "/" + project.getPer() + "/每月");
                    vo.setPaidTime(bill.getUpdateTime());
                    vo.setTotal(project.getPrice() * house.getUsageArea());
                    if (bill.getYear().equals(billByYearVo.getYear())){
                        billVos.add(vo);
                        billByYearVo.setPayList(billVos);
                    }else {
                        list.add(billByYearVo);
                        billByYearVo = new BillByYearVo();
                        billByYearVo.setYear(bill.getYear());
                        billVos = new ArrayList<>();
                        billVos.add(vo);
                        billByYearVo.setPayList(billVos);
                    }
                }
            }
            list.add(billByYearVo);
        }
        return Result.ok(list);
    }
}
