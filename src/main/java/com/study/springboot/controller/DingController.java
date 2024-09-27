package com.study.springboot.controller;

import com.study.springboot.common.R;
import com.study.springboot.domain.dto.DingDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.vo.DingVO;
import com.study.springboot.service.DingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dingb")
public class DingController {

    private final DingService dingService;

    @PostMapping("/page")
    public R pageDing(DingDTO dto){
        PageDTO<DingVO> page = dingService.pageDing(dto);
        return R.ok().put("page",page);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteDing(@PathVariable Long id){
        dingService.removeById(id);
        return R.ok();
    }

}
