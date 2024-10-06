package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.pojo.Season;
import com.study.springboot.service.SeasonService;
import com.study.springboot.mapper.SeasonMapper;
import org.springframework.stereotype.Service;

/**
* @author DS
* @description 针对表【season】的数据库操作Service实现
* @createDate 2024-10-05 23:27:11
*/
@Service
public class SeasonServiceImpl extends ServiceImpl<SeasonMapper, Season>
    implements SeasonService{

}




