package com.soft2242.system.vo;

import com.soft2242.system.entity.Equipment;
import com.soft2242.system.entity.Visitor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CodeListVo {
    private Integer id;
    private String uuid;
    private String codeName;
    private List<String> devices;
    private String effectiveTime;
    private Integer failure;
}
