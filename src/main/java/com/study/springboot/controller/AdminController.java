package com.study.springboot.controller;


import com.study.springboot.common.R;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.Admin;
import com.study.springboot.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public R adminLogin(LoginDTO dto){
        Admin admin = adminService.adminLogin(dto);
        return R.ok().put("admin",admin);
    }

    @PostMapping("/page")
    public R adminPage(AdminQuery query){
        PageDTO<Admin> page = adminService.adminPage(query);
        return R.ok().put("page",page);
    }

    @GetMapping("/get/{id}")
    public R getAdminById(@PathVariable Integer id){
        Admin admin = adminService.getById(id);
        return R.ok().put("admin",admin);
    }

    @PostMapping("/udpateAdmin")
    public R updateAdminByUsername(UserDTO dto){
        adminService.updateUserByAdminname(dto);
        return R.ok();
    }
}










