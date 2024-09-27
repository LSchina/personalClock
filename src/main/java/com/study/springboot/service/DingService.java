package com.study.springboot.service;

import com.study.springboot.domain.dto.DingDTO;
import com.study.springboot.domain.dto.PageDTO;
import com.study.springboot.domain.pojo.Ding;
import com.baomidou.mybatisplus.extension.service.IService;
import com.study.springboot.domain.vo.DingVO;

/**
* @author 86151
* @description 针对表【ding】的数据库操作Service
* @createDate 2024-06-20 13:30:40
*/
public interface DingService extends IService<Ding> {

    PageDTO<DingVO> pageDing(DingDTO dto);

    void addDing(Long id);

    PageDTO<DingVO> getDing(DingDTO query);
}
