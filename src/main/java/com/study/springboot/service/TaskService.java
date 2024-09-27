package com.study.springboot.service;

import com.study.springboot.domain.TaskQuery;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.SaveTaskDTO;
import com.study.springboot.domain.dto.TaskDTO;
import com.study.springboot.domain.pojo.Task;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.springboot.domain.vo.RecommendTask;
import com.study.springboot.domain.vo.TaskVO;

import java.text.ParseException;
import java.util.List;

/**
* @author 86151
* @description 针对表【task】的数据库操作Service
* @createDate 2024-06-20 13:30:59
*/
public interface TaskService extends IService<Task> {

    List<RecommendTask> recommendList();

    void exitTask(RecommendTask task);

    PageDTO<TaskVO> taskListPage(TaskQuery query);

    List<Task> taskRecommend();

    void addRec(Long id);

    void saveTask(SaveTaskDTO dto);

    List<Task> nowTask();

    Task autoSubmit(TaskDTO dto) throws ParseException;
}
