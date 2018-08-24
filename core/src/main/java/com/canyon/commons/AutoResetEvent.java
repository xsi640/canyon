package com.canyon.commons;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 通知正在等待的线程已发生事件。 此类不能被继承。
 * @author SuYang
 *
 */
public final class AutoResetEvent {
	private final Semaphore event;
	private final Integer mutex;
	private Object tag;

	/**
	 *  
	 * @param  signalled 若要将初始状态设置为终止状态; false 将初始状态设置为非终止。
	 */
	public AutoResetEvent(boolean signalled) {
		event = new Semaphore(signalled ? 1 : 0);
		mutex = new Integer(-1);
	}

	/**
	 * 将事件状态设置为有信号，从而允许一个或多个等待线程继续执行。
	 */
	public void set() {
		synchronized (mutex) {
			if (event.availablePermits() == 0) {
				event.release();
			}
		}
	}

	/**
	 * 将事件状态设置为非终止，从而导致线程受阻。
	 */
	public void reset() {
		event.drainPermits();
	}

	/**
	 * 阻止当前线程，直到当前 WaitHandle 收到信号。
	 * @throws InterruptedException
	 */
	public void waitOne() throws InterruptedException {
		event.acquire();
	}

	/**
	 * 阻止当前线程，直到当前 WaitHandle 收到信号。
	 * @param timeout 表示等待的时间
	 * @param unit 表示等待的时间单位
	 * @return
	 */
	public boolean waitOne(int timeout, TimeUnit unit) {
		try {
			return event.tryAcquire(timeout, unit);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 阻止当前线程，直到当前 WaitHandle 收到信号。
	 * @param timeout 表示等待的时间（单位：毫秒）
	 * @return
	 */
	public boolean waitOne(int timeout) {
		return waitOne(timeout, TimeUnit.MILLISECONDS);
	}

	/**
	 * 是否有等待的信号
	 * @return
	 */
	public boolean isSignalled() {
		return event.availablePermits() > 0;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}
}
