package com.soft2242.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddHouseVo {
    private Integer status;
    private Integer ownerId;
    private Integer communityId;
    private Integer buildingId;
    private Integer unit;
    private Integer roomNo;
    private String name;
    private Integer type;
    private Integer gender;
    private String phone;
    private String identityCard;
    private String photo;
}
