package com.wolf.book.ch3.scheduled;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by wolf on 16/12/28.
 */
@Configuration
@ComponentScan("com.wolf.book.ch3.scheduled")
@EnableScheduling
public class TaskSchedulerConfig {
}
