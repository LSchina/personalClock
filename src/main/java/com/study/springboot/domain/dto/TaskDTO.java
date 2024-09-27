package com.study.springboot.domain.dto;

import lombok.Data;

@Data
public class TaskDTO {

    private Long id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String createTime;

    /**
     *
     */
    private Long userId;

    /**
     *
     */
    private String comment;

    /**
     *
     */
    private String theme;

    /**
     * 0:系统推荐  1:进行中  2:已完成  3:已过期
     */
    private Integer status;

    /**
     *
     */
    private Long duration;
}
