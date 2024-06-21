package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.pojo.Ding;
import com.study.springboot.service.DingService;
import com.study.springboot.mapper.DingMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【ding】的数据库操作Service实现
* @createDate 2024-06-20 13:30:40
*/
@Service
public class DingServiceImpl extends ServiceImpl<DingMapper, Ding>
    implements DingService{

}




