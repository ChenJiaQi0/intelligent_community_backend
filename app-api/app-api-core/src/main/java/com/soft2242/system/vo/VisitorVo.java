package com.soft2242.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.soft2242.system.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VisitorVo{
    private Integer ownerId;
    private Integer propertyId;

    private List<Equipment> authorizedDevice;

    private Long authorizedTime;
    private List<Integer> doors;
    private Integer type;
}
