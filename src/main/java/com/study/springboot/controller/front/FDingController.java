package com.study.springboot.controller.front;

import com.study.springboot.common.R;
import com.study.springboot.domain.dto.DingDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.vo.DingVO;
import com.study.springboot.service.DingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fding")
public class FDingController {

    private final DingService dingService;

    @PostMapping("/add/{id}")
    public R addDing(@PathVariable Long id){
        dingService.addDing(id);
        return R.ok();
    }

    @PostMapping("/getding")
    public R getDing(DingDTO query){
        PageDTO<DingVO> page= dingService.getDing(query);
        return R.ok().put("page",page);
    }
}
