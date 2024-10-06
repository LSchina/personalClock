package com.study.springboot.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName poster
 */
@TableName(value ="poster")
@Data
public class Poster implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private String image;

    /**
     *
     */
    private String posterDesc;

    /**
     *
     */
    private Date createTime;
}