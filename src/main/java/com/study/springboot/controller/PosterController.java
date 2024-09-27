package com.study.springboot.controller;

import com.study.springboot.common.R;
import com.study.springboot.domain.pojo.Poster;
import com.study.springboot.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/poster")
public class PosterController {

    private final PosterService posterService;

    @GetMapping("/list")
    public R posterList(){
        List<Poster> list = posterService.list();
        return R.ok().put("list",list);
    }
}
