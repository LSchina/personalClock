package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.UserService;
import com.study.springboot.mapper.UserMapper;
import com.study.springboot.utils.CollUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 86151
* @description 针对表【user】的数据库操作Service实现
* @createDate 2024-06-20 13:30:32
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public PageDTO<User> userPage(AdminQuery query) {
        Page<User> page = lambdaQuery()
                .like(User::getNickname, query.getNickname())
                .like(User::getUsername, query.getUsername())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<User> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            PageDTO.empty(page);
        }
        return PageDTO.of(page);
    }

    @Override
    public void updateUserByUsername(UserDTO dto) {
        lambdaUpdate()
                .eq(User::getUsername,dto.getUsername())
                .set(User::getNickname,dto.getNickname())
                .set(User::getPassword,dto.getPassword())
                .set(User::getStataus,dto.getStataus())
                .set(User::getUpdateTime,new Date())
                .update();
    }
}




