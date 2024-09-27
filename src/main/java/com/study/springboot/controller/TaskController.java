package com.study.springboot.controller;

import com.study.springboot.common.R;
import com.study.springboot.domain.TaskQuery;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.domain.vo.RecommendTask;
import com.study.springboot.domain.vo.TaskVO;
import com.study.springboot.service.TaskService;
import com.study.springboot.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/btask")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/recommend")
    public R recommendList(){
        List<RecommendTask> list = taskService.recommendList();
        return R.ok().put("list",list);
    }

    @PostMapping("/exit")
    public R exitTask(RecommendTask task){
        taskService.exitTask(task);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    public R getById(@PathVariable Long id){
        Task byId = taskService.getById(id);
        RecommendTask task = BeanUtils.copyBean(byId, RecommendTask.class);
        return R.ok().put("task",task);
    }

    @PostMapping("/page")
    public R taskListPage(TaskQuery query){
        PageDTO<TaskVO> page = taskService.taskListPage(query);
        return R.ok().put("page",page);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteTaskById(@PathVariable Long id){
        taskService.removeById(id);
        return R.ok();
    }



}
