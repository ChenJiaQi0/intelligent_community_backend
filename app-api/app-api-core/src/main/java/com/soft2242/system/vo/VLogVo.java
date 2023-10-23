package com.soft2242.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class VLogVo {
    private String device;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date open_time;
    private Integer owner_id;
    private Integer code_id;
}
