package com.study.springboot.controller;


import com.study.springboot.common.R;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.UserService;
import com.study.springboot.utils.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @PostMapping("/login")
    public R userLogin(LoginDTO dto) throws UnsupportedEncodingException {
        User user = userService.userLogin(dto);
        String token = TokenUtils.genToken(String.valueOf(user.getId()), user.getPassword());
        return R.ok().put("user", user).put("token",token);
    }

    @PostMapping("/register")
    public R userRegister(LoginDTO dto){
        userService.userRegister(dto);
        return R.ok();
    }

    @GetMapping("/list")
    public R userList(){
        List<User> list = userService.list();
        return R.ok().put("list",list);
    }

    @PostMapping("/page")
    public R userPage(AdminQuery query){
        PageDTO<User> page = userService.userPage(query);
        return R.ok().put("page",page);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteUserById(@PathVariable Integer id){
        userService.removeById(id);
        return R.ok();
    }

    @GetMapping("/get/{id}")
    public R getUserById(@PathVariable Long id){
        User user = userService.getById(id);
        return R.ok().put("user",user);
    }

    @PostMapping("/udpateUser")
    public R updateUserByUsername(UserDTO dto){
        userService.updateUserByUsername(dto);
        return R.ok();
    }
}
