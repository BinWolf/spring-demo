package com.wolf.concurrent;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Created by wolf on 17/5/17.
 */
public class UserInfo implements Delayed{
	/**
	 * 延迟到未来的某个时间
	 */
	private long delayTime;
	private long id;
	public UserInfo(long delayTime, long id) {
		this.delayTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.SECONDS) + System.nanoTime();
		this.id = id;
	}
	public long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 控制延迟的时间，如果返回0或者负数可以take出来
	 * @param unit
	 * @return
	 */
	@Override
	public long getDelay(TimeUnit unit) {
		// unit 默认是 NANOSECONDS
		return unit.convert(delayTime - System.nanoTime(), TimeUnit.NANOSECONDS);
	}

	/**
	 * 放入DelayQueue中的排序规则
	 * @param o
	 * @return
	 */
	@Override
	public int compareTo(Delayed o) {
		UserInfo info = (UserInfo) o;
		long res = delayTime - info.getDelayTime();
		if (res > 0) {
			return 1;
		} else if (res < 0) {
			return -1;
		}
		return 0;
	}
	public static void main(String[] args) throws Exception {
		DelayQueue<UserInfo> delayQueue = new DelayQueue<>();
		UserInfo info2 = new UserInfo(2, 2);
		UserInfo info1 = new UserInfo(4, 4);
		delayQueue.offer(info2);
		delayQueue.offer(info1);
		while (true) {
			UserInfo info = delayQueue.take();
			System.out.println("date : " + new Date() + ";id = "+ info.getId());
		}
	}

}
