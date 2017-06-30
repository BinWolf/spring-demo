package com.wolf.controller;

import com.wolf.service.IDistributedLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wolf on 17/6/25.
 */

@RestController
@RequestMapping("lock")
public class LockController {

	@Autowired
	private IDistributedLockService lockService;

	@RequestMapping("/tryLock")
	public String tryLock() {
		String key = "memberIdLock";
		if (lockService.lock(key, true)) {
			System.out.println("拿到锁，执行任务。。。000000000。。。。");
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("release : " + lockService.release(key));
		} else {
			System.out.println("没有获取到锁!!!!!!!!!!!!!!");
		}

		return "tryLock";
	}

	@RequestMapping("/doLock")
	public String doLock() {
		String key = "memberIdLock";
		if (lockService.lock(key)) {
			System.out.println("拿到锁，执行任务。。。。。。。。。。。");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			System.out.println("release : " + lockService.release(key));
		} else {
			System.out.println("没有获取到锁!!!!!!!!!!!!!!");
		}
		return "odLock";
	}


}
