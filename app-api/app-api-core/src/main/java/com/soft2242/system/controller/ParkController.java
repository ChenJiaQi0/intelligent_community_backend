package com.soft2242.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.soft2242.base.common.exception.ServerException;
import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Car;
import com.soft2242.system.entity.Community;
import com.soft2242.system.entity.Park;
import com.soft2242.system.service.CarService;
import com.soft2242.system.service.CommunityService;
import com.soft2242.system.service.ParkService;
import com.soft2242.system.util.JwtUtil;
import com.soft2242.system.vo.CarVo;
import com.soft2242.system.vo.ParkVo;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/***
 * @description:我的车位
 * @author: yk
 **/

@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class ParkController {

    @Resource
    private ParkService parkService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private HttpServletRequest request;
    @Resource
    private CommunityService communityService;
    @Resource
    private CarService carService;

    /**
     * 添加车位
     */
    @PostMapping("addPark")
    public Result<Object> addPark(@RequestParam(value = "parkType", required = false) Integer parkType,
                                  @RequestBody Park park,
                                  @RequestParam(value = "owner_id", required = false) Integer ownerId) {
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (ownerId != null) {
            throw new ServerException("该车位被占据");
        }
        if (parkType == 0 && ownerId == null) {
            Community byId = communityService.getById(communityId);
            if (byId == null) {
                return Result.error("该小区没有绑定业主信息");
            }
            park.setOwnerId(ownerId);
            boolean flag = parkService.updateById(park);
            if (!flag) throw new ServerException("添加车位失败");
            log.info(park.toString());
        }
        if (parkType == 1 && ownerId == null) {
            Community byId = communityService.getById(communityId);
            if (byId == null) {
                return Result.error("该小区没有绑定业主信息");
            }
            park.setOwnerId(ownerId);
            boolean flag = parkService.updateById(park);
            if (!flag) throw new ServerException("服务器异常");
        }

        return Result.ok();
    }

    /**
     * 添加车辆
     */
    @PostMapping("addCar")
    public Result addCar(@RequestBody Car car) {
        log.info(car.toString());
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        if (car.getOwnerId() == null) {
            throw new ServerException("该业主没有购买车辆");
        }
        if (car.getOwnerId() != null) {
            carService.save(car);
            boolean flag = carService.updateById(car);
            if (!flag) throw new ServerException("服务器异常");
        }else {
            throw new ServerException("没有该业主信息");
        }
        return Result.ok();
    }

    /**
     * 展示车位信息
     */
//    @GetMapping("getPark")
//    public Result getPark(@RequestParam(value = "owner_id", required = false) Integer ownerId) {
//        String communityId = request.getHeader("community_id");
//        String token = request.getHeader("token");
//        //登录校验
//        Claims claims = jwtUtil.parseJWT(token);
//        if (claims.isEmpty()) throw new ServerException("请先登录");
//
//        if (ownerId != null) {
//            QueryWrapper<Park> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("community_id", communityId);
//            queryWrapper.eq("owner_id", ownerId);
////            queryWrapper.eq("car_id",)
////            QueryWrapper<Car> queryWrapper1 = new QueryWrapper<>();
////            List<Car> cars = carService.list();
////            List<Integer> list = new ArrayList<>();
////            for (Car car : cars) {
////                list.add(car.getId());
//////                QueryWrapper<Car> cwp = new QueryWrapper<>();
//////                cwp.eq("id", park.getCarId());
//////                Car car = carService.getOne(cwp);
//////                if (car.getOwnerId() == ownerId ) park.setCar(car);
////            }
////            List<Park> parks = parkService.listByIds(list);
//            List<Object> parkAndCar = new ArrayList<>();
//            List<Park> parks = parkService.list(queryWrapper);
//            for (Park park : parks) {
//                List<Object> list = new ArrayList<>();
//                list.add(park);
//
//                QueryWrapper<Car> cwp = new QueryWrapper<>();
//                cwp.eq("park_id", park.getCarId());
//                Car car = carService.getOne(cwp);
//                if (car != null) {
//                    if (car.getOwnerId() == ownerId) list.add(car);
//                }
//                parkAndCar.add(list);
//            }
//            return Result.ok(parkAndCar);
//        }
//        return Result.error();
//    }
    /**
     * 展示车位信息
     */
    @GetMapping("getPark")
    public Result getPark(@RequestParam(value = "owner_id", required = false) Integer ownerId) {
        String communityId = request.getHeader("community_id");
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        if (ownerId != null) {
            QueryWrapper<Park> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("community_id", communityId);
            queryWrapper.eq("owner_id", ownerId);
            List<Park> parks = parkService.list(queryWrapper);
            List<ParkVo> parkAndCar = new ArrayList<>();
            for (Park park : parks) {
//                List<Object> list = new ArrayList<>();
                ParkVo parkVo = new ParkVo();
                parkVo.setParkNo(park.getParkNo());
                parkVo.setParkSize(park.getParkSize());
                parkVo.setParkType(park.getParkType());
                parkVo.setGarageArea(park.getGarageArea());
                parkVo.setHasCar(park.getHasCar());
                parkVo.setIsPrivate(park.getIsPrivate());
                parkVo.setParkId(park.getId());
                QueryWrapper<Car> cwp = new QueryWrapper<>();
                cwp.eq("id", park.getCarId());
                cwp.eq("park_id", park.getId());
//                Car car = carService.getById(park.getCarId());
                Car car = carService.getOne(cwp);
                if (car != null) parkVo.setCar(car);
                parkAndCar.add(parkVo);

                log.info(parkVo.toString());
            }
            return Result.ok(parkAndCar);
        }
        return Result.error();
    }
    /**
      展示车辆信息
    */
    @GetMapping("/getCar")
    public Result getCar(@RequestParam(value = "owner_id") Integer ownerId,
                         @RequestParam(value = "number",required = false) Integer number) {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");

        if (number == null) {
            if (ownerId != null) {
                QueryWrapper<Car> wp = new QueryWrapper<>();
                wp.eq("owner_id", ownerId);
                List<Car> list = carService.list(wp);
                if (list.size() == 0) return Result.ok();
                return Result.ok(list);
            } else {
                throw new ServerException("没有该业主");
            }
        } else {
            QueryWrapper<Car> wp = new QueryWrapper<>();
            wp.eq("owner_id", ownerId).eq("number", number);
            Car car = carService.getOne(wp);
            if (car == null) throw new ServerException("查不到此车辆信息");
            return Result.ok(car);
        }

    }
    /**
     删除车辆
    */
    @PostMapping("/deleteCar")
    public Result<Object> deleteMembers(@RequestBody CarVo carVo) {
        String token = request.getHeader("token");
        //登录校验
        Claims claims = jwtUtil.parseJWT(token);
        if (claims.isEmpty()) throw new ServerException("请先登录");
        QueryWrapper<Car> wp = new QueryWrapper<>();
        wp.eq("id", carVo.getCarId());
        wp.eq("owner_id", carVo.getOwnerId());
        boolean remove = carService.remove(wp);
        if (!remove) {
            throw new ServerException("系统异常");
        }
        return Result.ok();
    }
}
