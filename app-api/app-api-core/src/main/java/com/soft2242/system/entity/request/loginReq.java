package com.soft2242.system.entity.request;

import lombok.Data;

@Data
public class loginReq {
    private Integer type;
    private String phone;
    private String password;
    private Integer code;
    private String newPassword;
}
