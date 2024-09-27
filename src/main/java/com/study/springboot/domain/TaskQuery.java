package com.study.springboot.domain;

import com.study.springboot.common.PageQuery;
import lombok.Data;

@Data
public class TaskQuery extends PageQuery {

    private String username;

    private String name;

    private String theme;
}
