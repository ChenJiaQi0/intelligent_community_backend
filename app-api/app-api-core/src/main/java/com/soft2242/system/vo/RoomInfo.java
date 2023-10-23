package com.soft2242.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomInfo {
    private Integer roomId;
    private Integer roomName;
    private String unit;
    private String type;
}
