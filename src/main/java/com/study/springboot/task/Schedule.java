package com.study.springboot.task;
 
import com.study.springboot.domain.pojo.Season;
import com.study.springboot.service.SeasonService;
import com.study.springboot.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 定时任务的使用
 **/
@Component
@RequiredArgsConstructor
public class Schedule {

    private final SeasonService seasonService;

    /**
     * 每月的一号生成新的赛季名称
     */
    @Scheduled(cron = "5 0 0 1 1/3 ? ")
    public void createSeason() {
        Season season = new Season();
        season.setName("第" + (seasonService.count() + 1) + "赛季");
        LocalDateTime now = LocalDateTime.now();
        season.setBeginTime(now);
        season.setOverTime(now.plusDays(30));
        seasonService.save(season);
    }


}