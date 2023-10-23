package com.soft2242.system.vo;

import com.soft2242.system.entity.Car;
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
public class ParkVo {
    private String garageArea;

    private String parkSize;

    private String parkType;

    private String parkNo;
    private Integer hasCar;
    private Integer isPrivate;
    private Integer parkId;
    private Car car;

}
