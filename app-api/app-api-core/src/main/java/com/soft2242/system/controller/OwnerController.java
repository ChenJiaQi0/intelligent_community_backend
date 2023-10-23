package com.soft2242.system.controller;

import com.soft2242.base.common.util.Result;
import com.soft2242.system.entity.Owner;
import com.soft2242.system.service.OwnerService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@Slf4j
@CrossOrigin
public class OwnerController {
    @Resource
    private OwnerService ownerService;

    /**
     * 住户信息
     */
    @GetMapping("/getOwnerById")
    public Result<Owner> getOwnerById(@RequestParam("owner_id") Integer ownerId){
        Owner owner = ownerService.getById(ownerId);
        return Result.ok(owner);
    }
}
