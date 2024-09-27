package com.study.springboot.domain.dto;

import lombok.Data;

@Data
public class SaveTaskDTO {

    private String name;

    private String theme;

    private String comment;

    private Integer duration;
}
