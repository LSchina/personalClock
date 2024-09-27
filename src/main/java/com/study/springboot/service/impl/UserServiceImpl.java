package com.study.springboot.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.UserService;
import com.study.springboot.mapper.UserMapper;
import com.study.springboot.utils.CollUtils;
import com.study.springboot.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.study.springboot.common.Constant.PASS_SALT;

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

    @Override
    public User userLogin(LoginDTO dto) {
        if (StringUtils.isEmpty(dto.getUsername()) && StringUtils.isEmpty(dto.getPassword())){
            throw new RuntimeException("用户名或密码为空");
        }
        User one = lambdaQuery().eq(User::getUsername, dto.getUsername()).one();
        if (one == null){
            throw new RuntimeException("该账号不存在");
        }
        if (!one.getPassword().equals(securePass(dto.getPassword()))){
            throw new RuntimeException("密码错误,请重新登录");
        }
        return one;

    }

    @Override
    public void userRegister(LoginDTO dto) {
        if (StringUtils.isEmpty(dto.getUsername())
                && StringUtils.isEmpty(dto.getPassword())
                && StringUtils.isEmpty(dto.getNewPass())){
            throw new RuntimeException("数据不能为空");
        }
        User one = lambdaQuery().eq(User::getUsername, dto.getUsername()).one();
        if (one != null){
            throw new RuntimeException("账号重复");
        }
        if (!dto.getPassword().equals(dto.getNewPass())){
            throw new RuntimeException("请再次输入密码");
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setNickname(dto.getUsername());
        user.setStataus(1);
        user.setPassword(securePass(dto.getPassword()));
        save(user);
    }

    //输入的密码转化为MD5加密后的密码的自定义方法
    private String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }
}




