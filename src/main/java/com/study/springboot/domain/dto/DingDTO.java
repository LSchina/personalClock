package com.study.springboot.domain.dto;

import com.study.springboot.common.PageQuery;
import lombok.Data;

@Data
public class DingDTO extends PageQuery {

    private String username;

    private String theme;

}
