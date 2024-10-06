package com.study.springboot.controller;


import com.study.springboot.common.R;
import com.study.springboot.domain.AdminQuery;
import com.study.springboot.domain.dto.CarouselDTO;
import com.study.springboot.domain.dto.LoginDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.dto.UserDTO;
import com.study.springboot.domain.pojo.Poster;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.service.UserService;
import com.study.springboot.utils.TokenUtils;
import com.study.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    //FILE_PATH存放图片文件的固定地址
    private static final String FILE_PATH = "D:\\实训\\个人自律springboot\\src\\main\\resources\\static\\image";

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

    /**
 * 上传头像
 */
     @PostMapping("/uploadAvater")
     public R CarouselAdd(@RequestPart("file") MultipartFile file){
         //获取上传的文件原始名
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
         //avater全局变量
         String avater ="";
         try {
             file.transferTo(myFile);
             avater = "http://localhost:9091/image/" + fileName;
             User user = new User();
             //UserContext
             Long userId = UserContext.getUser();
             user.setId(userId);
             user.setAvater(avater);
             userService.updateById(user);
         } catch (IOException e) {
             e.printStackTrace();
             return R.error("系统出错！！！");
         }
         //返回avater给前端
         return R.ok().put("avater",avater);
     }
}


