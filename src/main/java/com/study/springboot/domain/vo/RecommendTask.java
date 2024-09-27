package com.study.springboot.domain.vo;

import lombok.Data;
import java.util.Date;

@Data
public class RecommendTask {

    private Long id;

    private String theme;

    private String name;

    private String comment;

    private Date createTime;
}
