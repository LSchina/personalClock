package com.study.springboot.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class TaskVO {

    private Long id;

    private String name;

    private String username;

    private String theme;

    private Date createTime;

    private Integer status;

    private String comment;

    private Long duration;
}
