package com.study.springboot.service;

import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoignDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 86151
* @description 针对表【admin】的数据库操作Service
* @createDate 2024-06-20 13:22:32
*/
public interface AdminService extends IService<Admin> {

    Admin adminLogin(LoignDTO dto);

    PageDTO<Admin> adminPage(AdminQuery query);

    void updateUserByAdminname(UserDTO dto);
}
