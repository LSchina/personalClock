package com.study.springboot.controller;


import com.study.springboot.common.R;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.CarouselDTO;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.Admin;
import com.study.springboot.domain.pojo.Poster;
import com.study.springboot.domain.pojo.Task;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PosterService posterService;

    private final AdminService adminService;

    private final UserService userService;

    private final TaskService taskService;

    private final DingService dingService;

    private final SeasonService seasonService;

    private static final String FILE_PATH = "D:\\实训\\个人自律springboot\\src\\main\\resources\\static\\image";

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
    @GetMapping("/posterList")
    public R posterList(){
        List<Poster> list = posterService.list();
        return R.ok().put("list",list);
    }
    @GetMapping("/posterGet/{id}")
    public R getPosterById(@PathVariable Long id){
        Poster poster= posterService.getById(id);
        return R.ok().put("poster",poster);

    }

    @DeleteMapping("/posterDelete/{id}")
    public R deletePosterById(@PathVariable Long id){
        posterService.removeById(id);
        return R.ok();
    }

    @PostMapping("/editPoster")
    public R CarouselAdd(@RequestPart("file") MultipartFile file, CarouselDTO dto){
        //获取上传的文件原始名  //pjijn.jpg
        String originalFilename = file.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.lastIndexOf(".")); //.jpg
        //创建一个新的文件名
        String fileName = UUID.randomUUID() + type;
        File targetFile = new File(FILE_PATH);
        //如果不存在，就创建一个新的文件
        if (!targetFile.exists()){
            targetFile.mkdir();
        }
        File myFile = new File(targetFile, fileName);
        try {
            file.transferTo(myFile);
            String image = "http://localhost:9091/image/" + fileName;
            dto.setImage(image);
            Poster poster = new Poster();
            poster.setId(dto.getId());
            poster.setImage(dto.getImage());
            poster.setUrl(dto.getUrl());
            poster.setPosterDesc(dto.getPosterDesc());
            posterService.updateById(poster);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("系统出错！！！");
        }
        return R.ok();
    }

    @GetMapping("/chart")
    public R chart(){
        List<Integer> list = new ArrayList<>();
        list.add(userService.count());
        list.add(adminService.count());
        list.add(taskService.count());
        list.add(posterService.count());
        list.add(dingService.count());
        list.add(2);
        List<Integer> list1 = new ArrayList<>();
        Integer count = taskService.lambdaQuery().eq(Task::getStatus, 1).count();
        Integer count1 = taskService.lambdaQuery().eq(Task::getStatus, 2).count();
        Integer count2 = taskService.lambdaQuery().eq(Task::getStatus, 3).count();
        list1.add(count);
        list1.add(count1);
        list1.add(count2);
        return R.ok().put("list",list).put("list1",list1);
    }

    @GetMapping("/season")
    public R season(){
        return R.ok().put("season",seasonService.list());
    }

}










