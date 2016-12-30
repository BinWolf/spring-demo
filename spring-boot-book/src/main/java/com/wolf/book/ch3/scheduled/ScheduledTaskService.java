package com.wolf.book.ch3.scheduled;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wolf on 16/12/28.
 */
@Service
public class ScheduledTaskService {

    @Scheduled(fixedRate = 5000) // 每5秒执行一次
    public void reportCurrentTime() throws Exception{
        System.out.println("每隔5秒执行一次：" +DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(new Date()));
    }

    /**
     * 格式: [秒] [分] [小时] [日] [月] [周] [年]
     * 1  秒  是  0-59    , - * /
     * 2  分  是  0-59   , - * /
     * 3  时  是  0-23   , - * /
     * 4  日  是  1-31   , - * ? / L W
     * 5  月  是  1-12 or JAN-DEC   , - * /
     * 6  周  是  1-7 or SUN-SAT   , - * ? / L #
     * 7  年  否  empty 或 1970-2099  , - * /
     */

    @Scheduled(cron = "0/2 * * ? * *")
    public void fixTimeExecution(){
        System.out.println("在指定时间 ：" + DateFormatUtils.ISO_TIME_NO_T_FORMAT.format(new Date()));
    }
}
