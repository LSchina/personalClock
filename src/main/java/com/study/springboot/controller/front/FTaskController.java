package com.study.springboot.controller.front;

import com.study.springboot.common.R;
import com.study.springboot.controller.TaskController;
import com.study.springboot.domain.dto.SaveTaskDTO;
import com.study.springboot.domain.dto.TaskDTO;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class FTaskController {

    private final TaskService taskService;

    @GetMapping("/RecList")
    public R RecList(){
        List<Task> list = taskService.taskRecommend();
        return R.ok().put("list",list);
    }

    @PostMapping("/add/{id}")
    public R addRec(@PathVariable Long id){
        taskService.addRec(id);
        return R.ok();
    }

    @PostMapping("/saveTask")
    public R saveTask(SaveTaskDTO dto){
        taskService.saveTask(dto);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    public R getById(@PathVariable Long id){
        Task task = taskService.getById(id);
        return R.ok().put("task",task);
    }

    @GetMapping("/nowTask")
    public R nowList(){
        List<Task> list = taskService.nowTask();
        return R.ok().put("list",list);
    }
    @PostMapping("/autoSubmit")
    public R autoSubmit(TaskDTO dto) throws ParseException {
        Task task = taskService.autoSubmit(dto);
        return R.ok().put("task",task);
    }

}
