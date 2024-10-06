package com.study.springboot.controller.front;

import com.study.springboot.common.R;
import com.study.springboot.domain.vo.RankVO;
import com.study.springboot.service.RankingService;
import com.study.springboot.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rank")
public class RankController {

    private final RankingService rankingService;

    private final SeasonService seasonService;

    @GetMapping("/self")
    public R selfRank() {
        RankVO vo = rankingService.selfRank();
        if (vo == null){
            return R.ok().put("vo",' ');
        }
        return R.ok().put("vo",vo);
    }

    @GetMapping("/season")
    public R seasonRank() {
        return R.ok().put("count",seasonService.count());
    }

    @GetMapping("/ranking")
    public R ranking(){
        return R.ok().put("voList",rankingService.ranking());
    }


}
