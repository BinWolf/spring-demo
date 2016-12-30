package com.wolf.book.ch3.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Created by wolf on 16/12/28.
 */
@Service
public class AsyncTaskService {

    @Async // 表明该方法是异步方法，使用ThreadPoolTaskExecutor作为TaskExecutor
    public void executeAsyncTask(int i) {
        System.out.println("异步执行任务 ：" + i);
    }

    @Async
    public void executeAsyncTaskPlus(int i) {
        System.out.println("异步执行任务 +1：" + (i + 1));
    }


}
