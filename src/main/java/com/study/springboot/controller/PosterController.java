package com.study.springboot.controller;

import com.study.springboot.common.R;
import com.study.springboot.domain.dto.CarouselDTO;
import com.study.springboot.domain.pojo.Poster;
import com.study.springboot.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poster")
public class PosterController {

    private final PosterService posterService;

    private static final String FILE_PATH = "D:\\实训\\个人自律springboot\\src\\main\\resources\\static\\image";


    @GetMapping("/list")
    public R posterList(){
        List<Poster> list = posterService.list();
        return R.ok().put("list",list);
    }

    /**
     * 添加广告
     * file:
     * {
     *     json:
     * }
     */
    @PostMapping("/addPoster")
    public R CarouselAdd(@RequestPart("file") MultipartFile file,CarouselDTO dto){
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
            poster.setUrl(dto.getUrl());
            poster.setPosterDesc(dto.getPosterDesc());
            posterService.save(poster);
        } catch (IOException e) {
            e.printStackTrace();
            return R.error("系统出错！！！");
        }
        return R.ok();
    }
}
