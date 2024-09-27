package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.dto.DingDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.pojo.Ding;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.domain.vo.DingVO;
import com.study.springboot.service.DingService;
import com.study.springboot.mapper.DingMapper;
import com.study.springboot.service.TaskService;
import com.study.springboot.service.UserService;
import com.study.springboot.utils.BeanUtils;
import com.study.springboot.utils.CollUtils;
import com.study.springboot.utils.StringUtils;
import com.study.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 86151
* @description 针对表【ding】的数据库操作Service实现
* @createDate 2024-06-20 13:30:40
*/
@Service
@RequiredArgsConstructor
public class DingServiceImpl extends ServiceImpl<DingMapper, Ding>
    implements DingService{

    private final UserService userService;

    private final TaskService taskService;

    @Override
    public PageDTO<DingVO> pageDing(DingDTO dto) {
        List<User> list = userService.lambdaQuery()
                .like(StringUtils.isNotBlank(dto.getUsername()), User::getUsername, dto.getUsername())
                .list();
        Set<Long> collect = list.stream().map(User::getId).collect(Collectors.toSet());
        Map<Long, User> userMap = list.stream().collect(Collectors.toMap(User::getId, item -> item));
        List<Task> taskList = taskService.lambdaQuery()
                .like(StringUtils.isNotBlank(dto.getTheme()), Task::getTheme, dto.getTheme())
                .list();
        Set<Long> collect1 = taskList.stream().map(Task::getId).collect(Collectors.toSet());
        Map<Long, Task> taskMap = taskList.stream().collect(Collectors.toMap(Task::getId, item -> item));
        Page<Ding> page = lambdaQuery().in(Ding::getUserId, collect)
                .in(Ding::getTaskId, collect1)
                .page(dto.toMpPageDefaultSortByCreateTimeDesc());
        List<Ding> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        List<DingVO> voList = new ArrayList<>();
        for (Ding record : records) {
            DingVO vo = BeanUtils.copyBean(record, DingVO.class);
            User user = userMap.get(record.getUserId());
            Task task = taskMap.get(record.getTaskId());
            vo.setTheme(task.getTheme());
            vo.setName(task.getName());
            vo.setComment(task.getComment());
            vo.setFinishTime(record.getCreateTime());
            vo.setUsername(user.getUsername());
            voList.add(vo);
        }
        return PageDTO.of(page,voList);
    }

    @Override
    public void addDing(Long id) {
        Long userId = UserContext.getUser();
        Ding ding = new Ding();
        ding.setUserId(userId);
        ding.setTaskId(id);
        Task task = taskService.getById(id);
        if (task == null){
            throw new RuntimeException("数据异常!!!");
        }
        ding.setTimeCou(30*60 - task.getDuration());
        boolean b = taskService
                .lambdaUpdate()
                .eq(Task::getId, id)
                .set(Task::getStatus, 2)
                .update();
        if (!b){
            throw new RuntimeException("打卡失败！！！");
        }
        save(ding);
    }

    @Override
    public PageDTO<DingVO> getDing(DingDTO query) {
        Long userId = UserContext.getUser();
        //根据主题获取任务id
        List<Task> list = taskService.lambdaQuery()
                .like(StringUtils.isNotBlank(query.getTheme()),Task::getTheme, query.getTheme())
                .eq(Task::getUserId,userId)
                .eq(Task::getStatus,2)
                .list();
        //使用stream流获取主题id
        Set<Long> collect = list.stream().map(Task::getId).collect(Collectors.toSet());
        Map<Long, Task> taskMap = list.stream().collect(Collectors.toMap(Task::getId, item -> item));
        //当前线程用户userId

        Page<Ding> page = lambdaQuery()
                .eq(Ding::getUserId, userId)
                .in(Ding::getTaskId,collect)
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Ding> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            return PageDTO.empty(page);
        }
        List<DingVO> voList = new ArrayList<>();
        for (Ding record : records) {
            Task task = taskMap.get(record.getTaskId());
            DingVO vo = BeanUtils.copyBean(record, DingVO.class);
            vo.setComment(task.getComment());
            vo.setName(task.getName());
            vo.setTheme(task.getTheme());
            vo.setFinishTime(record.getCreateTime());
            voList.add(vo);
        }
        return PageDTO.of(page,voList);
    }
}




