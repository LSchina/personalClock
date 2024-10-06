package com.study.springboot.service;

import com.study.springboot.domain.pojo.Ranking;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.springboot.domain.vo.RankVO;
import com.study.springboot.domain.vo.RankingVO;
import org.apache.catalina.LifecycleState;

import java.util.List;

/**
* @author DS
* @description 针对表【ranking】的数据库操作Service
* @createDate 2024-10-05 23:43:24
*/
public interface RankingService extends IService<Ranking> {

    RankVO selfRank();

    List<RankingVO> ranking();
}
