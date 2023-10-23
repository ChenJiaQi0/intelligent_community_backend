package com.soft2242.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuildingInfo {
    private Integer buildingId;
    private String buildingName;
    private List<RoomInfo> rooms;
}
