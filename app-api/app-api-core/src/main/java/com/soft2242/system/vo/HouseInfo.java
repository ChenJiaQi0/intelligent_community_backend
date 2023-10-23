package com.soft2242.system.vo;

import com.soft2242.system.entity.Building;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseInfo {
    private Integer communityId;
    private String communityName;
    private List<BuildingInfo> buildings;
}
