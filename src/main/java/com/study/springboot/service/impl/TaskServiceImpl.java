package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.service.TaskService;
import com.study.springboot.mapper.TaskMapper;
import org.springframework.stereotype.Service;

/**
* @author 86151
* @description 针对表【task】的数据库操作Service实现
* @createDate 2024-06-20 13:30:59
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

}




