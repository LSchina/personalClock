package com.study.springboot.domain.vo;

import com.study.springboot.common.PageQuery;
import lombok.Data;

import java.util.Date;

@Data
public class DingVO extends PageQuery {

    private Long id;

    private String theme;

    private String name;

    private String comment;

    private Date finishTime;

    private Long timeCou;

    private String username;
}
