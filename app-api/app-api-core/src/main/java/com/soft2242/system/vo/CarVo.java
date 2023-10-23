package com.soft2242.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * @description:
 * @author: yk
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CarVo {
    private Integer carId;
    private Integer ownerId;
}
