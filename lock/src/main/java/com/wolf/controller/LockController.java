package com.wolf.controller;

import com.wolf.service.IDistributedLockService;
import com.wolf.service.impl.ZkLockServiceImpl;
import com.wolf.service.impl.ZookLockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wolf on 17/6/25.
 */

@RestController
@RequestMapping("lock")
public class LockController {

	private static ExecutorService threadPool = Executors.newFixedThreadPool(10000);
	@Autowired
	private IDistributedLockService lockService;

	@Autowired
	private ZookLockServiceImpl zkLockService;

	@RequestMapping("/zkLock")
	public String zkLock() {
		testZK();
		String key = "memberIdLock";
		return "zkLock";
	}

	private void testZK() {
		long s = System.currentTimeMillis();
		//模拟5000个线程
		for (int i = 0; i < 200; i++) {
			final int task = i;
			threadPool.execute(
					new Runnable() {
						@Override
						public void run() {
							String key = "memberIdLock";
							if (zkLockService.lock(key)) {
								try {
									System.out.println("拿到锁，执行任务。。。" + task);
									Thread.sleep(20);
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									System.out.println(task + " release : " + zkLockService.release(key));
								}
							} else {
								System.out.println(task + "没有获取到锁!!!!!!!!!!!!!!");
							}
						}
					});
		}
		long e = System.currentTimeMillis();
		System.out.println("花费时间：" + (e - s));
	}

	@RequestMapping("/tryLock")
	public String tryLock() {
		test();
		return "tryLock";
	}

	private void test() {
		long s = System.currentTimeMillis();
		//模拟5000个线程
		for (int i = 0; i < 200; i++) {
			final int task = i;
			threadPool.execute(
					new Runnable() {
						@Override
						public void run() {
							String key = "memberIdLock";
							if (lockService.lock(key + task, true)) {
								try {
									System.out.println("拿到锁，执行任务。。。" + task);
									Thread.sleep(20);
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									System.out.println(task + " release : " + lockService.release(key));
								}
							} else {
								System.out.println(task + "没有获取到锁!!!!!!!!!!!!!!");
							}
						}
					});
		}
		long e = System.currentTimeMillis();
		System.out.println("花费时间：" + (e - s));
	}

}
