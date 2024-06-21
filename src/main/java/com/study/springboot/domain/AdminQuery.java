package com.study.springboot.domain;

import com.study.springboot.common.PageQuery;
import lombok.Data;

@Data
public class AdminQuery extends PageQuery {

    private String username;

    private String nickname;
}
