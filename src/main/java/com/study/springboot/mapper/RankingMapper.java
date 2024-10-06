package com.study.springboot.mapper;

import com.study.springboot.domain.pojo.Ranking;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.springboot.domain.vo.RankVO;

import java.util.List;

/**
* @author DS
* @description 针对表【ranking】的数据库操作Mapper
* @createDate 2024-10-05 23:43:24
* @Entity com.study.springboot.domain.pojo.Ranking
*/
public interface RankingMapper extends BaseMapper<Ranking> {

    RankVO selfRank(Long userId);

    List<RankVO> ranking();
}




