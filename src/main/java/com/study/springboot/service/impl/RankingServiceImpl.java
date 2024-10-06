package com.study.springboot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.springboot.domain.pojo.Ranking;
import com.study.springboot.domain.pojo.User;
import com.study.springboot.domain.vo.RankVO;
import com.study.springboot.domain.vo.RankingVO;
import com.study.springboot.service.RankingService;
import com.study.springboot.mapper.RankingMapper;
import com.study.springboot.service.UserService;
import com.study.springboot.utils.CollUtils;
import com.study.springboot.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author DS
* @description 针对表【ranking】的数据库操作Service实现
* @createDate 2024-10-05 23:43:24
*/
@Service
@RequiredArgsConstructor
public class RankingServiceImpl extends ServiceImpl<RankingMapper, Ranking>
    implements RankingService{

    private final RankingMapper rankingMapper;

    private final UserService userService;

    @Override
    public RankVO selfRank() {
        Long userId = UserContext.getUser();
        return rankingMapper.selfRank(userId);
    }

    @Override
    public List<RankingVO> ranking() {
        List<RankVO> ranking = rankingMapper.ranking();
        if (CollUtils.isEmpty(ranking)){
            throw new RuntimeException("排行榜为空");
        }
        Set<Long> collect = ranking.stream().map(RankVO::getUserId).collect(Collectors.toSet());
        List<User> users = userService.getBaseMapper().selectBatchIds(collect);
        if (CollUtils.isEmpty(users)){
            throw new RuntimeException("系统错误");
        }
        Map<Long, String> userMap = users.stream().collect(Collectors.toMap(User::getId, User::getNickname));

        List<RankingVO> voList = new ArrayList<>();
        for (RankVO vo : ranking) {
            RankingVO rankingVO = new RankingVO();
            rankingVO.setRanking(vo.getRanking());
            rankingVO.setNickname(userMap.get(vo.getUserId()));
            voList.add(rankingVO);
        }
        return voList;
    }
}




