package com.soft2242.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillVo {
    private Integer billId;
    private String projectName;
    private String house;
    private String houseArea;
    private String price;
    @JsonFormat(pattern = "yyyy年MM月dd号")
    private Date startTime;
    @JsonFormat(pattern = "yyyy年MM月dd日")
    private Date endTime;
    private Double total;
    private String icon;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date paidTime;

}
