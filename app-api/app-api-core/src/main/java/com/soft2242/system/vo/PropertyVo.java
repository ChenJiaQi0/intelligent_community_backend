package com.soft2242.system.vo;

import com.soft2242.system.entity.Community;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/***
 * @description:
 * @author: yk
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyVo {
    private String telephone;
    private Community community;
    private List<CompanyVo> companyVos;
}
