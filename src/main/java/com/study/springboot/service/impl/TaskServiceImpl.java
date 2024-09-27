package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.TaskQuery;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.SaveTaskDTO;
import com.study.springboot.domain.dto.TaskDTO;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.domain.vo.RecommendTask;
import com.study.springboot.domain.vo.TaskVO;
import com.study.springboot.service.TaskService;
import com.study.springboot.mapper.TaskMapper;
import com.study.springboot.service.UserService;
import com.study.springboot.utils.BeanUtils;
import com.study.springboot.utils.CollUtils;
import com.study.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author 86151
* @description 针对表【task】的数据库操作Service实现
* @createDate 2024-06-20 13:30:59
*/
@Service
@RequiredArgsConstructor
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService{

    private final UserService userService;

    @Override
    public List<RecommendTask> recommendList() {
        List<Task> list = lambdaQuery().eq(Task::getStatus, 0).list();
        List<RecommendTask> taskList = BeanUtils.copyList(list, RecommendTask.class);
        return taskList;
    }

    @Override
    public void exitTask(RecommendTask task) {
        boolean update = lambdaUpdate()
                .eq(Task::getId, task.getId())
                .set(Task::getTheme, task.getTheme())
                .set(Task::getName, task.getName())
                .set(Task::getComment, task.getComment())
                .update();
        if (!update){
            throw new RuntimeException("修改错误！！！");
        }
    }

    @Override
    public PageDTO<TaskVO> taskListPage(TaskQuery query) {

        List<User> list = userService.lambdaQuery().like(User::getUsername, query.getUsername()).list();
        Set<Long> userdList = list.stream().map(User::getId).collect(Collectors.toSet());

        Page<Task> page = lambdaQuery()
                .like(Task::getName, query.getName())
                .in(Task::getUserId, userdList)
                .like(Task::getTheme, query.getTheme())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Task> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        List<TaskVO> voList = new ArrayList<>();
        Map<Long, String> userMap = list.stream().collect(Collectors.toMap(User::getId, item -> item.getUsername()));
        for (Task task : records) {
            TaskVO vo = BeanUtils.copyBean(task, TaskVO.class);
            vo.setUsername(userMap.get(task.getUserId()));
            voList.add(vo);
        }
        return PageDTO.of(page,voList);
    }

    @Override
    public List<Task> taskRecommend() {
        List<Task> list = lambdaQuery().eq(Task::getStatus, 0).list();
        return list;
    }

    @Override
    public void addRec(Long id) {

        Long userId = UserContext.getUser();
        Task one = lambdaQuery()
                .eq(Task::getId, id)
                .eq(Task::getStatus, 0)
                .one();
        Task one1 = lambdaQuery()
                .eq(Task::getUserId, userId)
                .eq(Task::getComment, one.getComment())
                .eq(Task::getStatus,1)
                .one();
        if (one1 != null) {
            throw new RuntimeException("你已有此计划");
        }
        Task task = new Task();
        task.setName(one.getName());
        task.setComment(one.getComment());
        task.setTheme(one.getTheme());
        task.setUserId(userId);
        task.setStatus(1);
        task.setDuration(30 * 60L);
        save(task);
    }

    @Override
    public void saveTask(SaveTaskDTO dto) {
        Long userId = UserContext.getUser();
        Task task = new Task();
        task.setUserId(userId);
        task.setName(dto.getName());
        task.setComment(dto.getComment());
        task.setTheme(dto.getTheme());
        task.setStatus(1);
        task.setDuration(dto.getDuration() * 60L);
        save(task);
    }

    @Override
    public List<Task> nowTask() {
        Long userId = UserContext.getUser();
        List<Task> list = lambdaQuery()
                .eq(Task::getUserId, userId)
                .eq(Task::getStatus, 1)
                .list();
        return list;
    }

    @Override
    @Transactional
    public Task autoSubmit(TaskDTO one) throws ParseException {
        if (one == null) {
            return null;
        }
        long time = new Date().getTime();
        //简单的Date类型构造器
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        //解析String类型变为Date类型
        Date date = sdf.parse(one.getCreateTime());
        long createTime = date.getTime();
        one.setDuration(30*60 - (time - createTime) / 1000);
        if (time >= (createTime + 30 * 60 * 1000)) {
            one.setStatus(3);
        }
        Task one1 = lambdaQuery()
                .eq(Task::getId, one.getId())
                .one();
        one1.setStatus(one.getStatus());
        one1.setDuration(one.getDuration());
        boolean b = updateById(one1);
        if (!b) {
            throw new RuntimeException("无法建立心跳请求");
        }
        return lambdaQuery().eq(Task::getId,one.getId()).one();
    }
}




