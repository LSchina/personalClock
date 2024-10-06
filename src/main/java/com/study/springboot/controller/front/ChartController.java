package com.study.springboot.controller.front;

import com.study.springboot.common.R;
import com.study.springboot.domain.pojo.Ding;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.service.DingService;
import com.study.springboot.service.TaskService;
import com.study.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chart")
@RequiredArgsConstructor
public class ChartController {

    private final TaskService taskService;

    private final DingService dingService;

    @GetMapping("/one")
    public R chartOne(){
        Long userId = UserContext.getUser();
        List<Integer> list = new ArrayList<>();
        Integer count = taskService.lambdaQuery()
                .gt(Task::getCreateTime, LocalDate.now())
                .lt(Task::getCreateTime, LocalDateTime.now())
                .eq(Task::getUserId, userId).eq(Task::getStatus, 1).count();
        Integer count1 = taskService.lambdaQuery()
                .gt(Task::getCreateTime, LocalDate.now())
                .lt(Task::getCreateTime, LocalDateTime.now())
                .eq(Task::getUserId, userId).eq(Task::getStatus, 2).count();
        Integer count2 = taskService.lambdaQuery()
                .gt(Task::getCreateTime, LocalDate.now())
                .lt(Task::getCreateTime, LocalDateTime.now())
                .eq(Task::getUserId, userId).eq(Task::getStatus, 3).count();
        Integer count3 = taskService.lambdaQuery()
                .gt(Task::getCreateTime, LocalDate.now())
                .lt(Task::getCreateTime, LocalDateTime.now())
                .eq(Task::getUserId, userId).count();
        Integer count4 = dingService.lambdaQuery().eq(Ding::getUserId, userId)
                .gt(Ding::getCreateTime, LocalDate.now())
                .lt(Ding::getCreateTime, LocalDateTime.now())
                .count();
        list.add(count);
        list.add(count1);
        list.add(count2);
        list.add(count3);
        list.add(count4);
        return R.ok().put("list", list);
    }

    @GetMapping("/two")
    public R chartTwo(){
        Long userId = UserContext.getUser();
        List<Integer> list = new ArrayList<>();
        Integer count = dingService.lambdaQuery().eq(Ding::getUserId, userId)
                .ge(Ding::getTimeCou, 0)
                .le(Ding::getTimeCou, 600)
                .count();
        Integer count1 = dingService.lambdaQuery().eq(Ding::getUserId, userId)
                .ge(Ding::getTimeCou, 601)
                .le(Ding::getTimeCou, 1200)
                .count();
        Integer count2 = dingService.lambdaQuery().eq(Ding::getUserId, userId)
                .ge(Ding::getTimeCou, 1201)
                .le(Ding::getTimeCou, 1500)
                .count();
        Integer count3 = dingService.lambdaQuery().eq(Ding::getUserId, userId)
                .ge(Ding::getTimeCou, 1501)
                .count();
        list.add(count);
        list.add(count1);
        list.add(count2);
        list.add(count3);
        return R.ok().put("list", list);
    }
}
