package com.study.springboot.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.common.R;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoignDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.Admin;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.AdminService;
import com.study.springboot.mapper.AdminMapper;
import com.study.springboot.utils.CollUtils;
import com.study.springboot.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 86151
* @description 针对表【admin】的数据库操作Service实现
* @createDate 2024-06-20 13:22:32
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{

    private static final String PASS_SALT = "songjunhao";

    @Override
    public Admin adminLogin(LoignDTO dto) {
        if (StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getPassword())){
            throw new RuntimeException("账号或者密码为空");
        }
        Admin one = lambdaQuery()
                .eq(Admin::getUsername, dto.getUsername())
                .one();
        if (one == null){
            throw new RuntimeException("账号不存在");
        }
        if (!one.getPassword().equals(dto.getPassword())){
            throw new RuntimeException("您的密码为空");
        }
        return one;
    }

    @Override
    public PageDTO<Admin> adminPage(AdminQuery query) {
        Page<Admin> page = lambdaQuery()
                .like(Admin::getNickname, query.getNickname())
                .like(Admin::getUsername, query.getUsername())
                .page(query.toMpPageDefaultSortByCreateTimeDesc());
        List<Admin> records = page.getRecords();
        if (CollUtils.isEmpty(records)){
            PageDTO.empty(page);
        }
        return PageDTO.of(page);
    }

    @Override
    public void updateUserByAdminname(UserDTO dto) {
        lambdaUpdate()
                .eq(Admin::getUsername,dto.getUsername())
                .set(Admin::getNickname,dto.getNickname())
                .set(Admin::getPassword,dto.getPassword())
                .set(Admin::getUpdateTime,new Date())
                .update();
    }


    //输入的密码转化为MD5加密后的密码的自定义方法
    private String securePass(String password){
        return SecureUtil.md5(password + PASS_SALT);
    }
}




