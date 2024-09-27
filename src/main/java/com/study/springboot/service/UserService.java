package com.study.springboot.service;

import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.UnsupportedEncodingException;

/**
* @author 86151
* @description 针对表【user】的数据库操作Service
* @createDate 2024-06-20 13:30:32
*/
public interface UserService extends IService<User> {

    PageDTO<User> userPage(AdminQuery query);

    void updateUserByUsername(UserDTO dto);

    User userLogin(LoginDTO dto) throws UnsupportedEncodingException;

    void userRegister(LoginDTO dto);
}
