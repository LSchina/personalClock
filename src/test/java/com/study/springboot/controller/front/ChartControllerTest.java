package com.study.springboot.controller.front;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChartControllerTest {

    @Test
    void chartOne() {

        Date date = new Date();
        // 创建SimpleDateFormat对象
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 格式化日期
        String formattedDate = sdf.format(date);

        // 输出格式化后的日期
        System.out.println(formattedDate);
    }
}