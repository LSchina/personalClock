package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.pojo.Poster;
import com.study.springboot.service.PosterService;
import com.study.springboot.mapper.PosterMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【poster】的数据库操作Service实现
* @createDate 2024-06-20 13:30:50
*/
@Service
public class PosterServiceImpl extends ServiceImpl<PosterMapper, Poster>
    implements PosterService{

}




