package com.study.springboot.domain.dto;

import lombok.Data;

@Data
public class UserDTO {

    private String username;

    private String nickname;

    private String password;

    private Integer stataus;
}
