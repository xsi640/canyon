package com.canyon.commons;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class ThreadUtils {
	public static void run(final Runnable runnable) {
		run(runnable, null, 0);
	}

	public static void run(final Runnable runnable, final long milliseconds) {
		run(runnable, null, milliseconds);
	}

	public static <T> void run(final FutureTask<T> future) {
		if (future == null)
			return;
		Thread thread = new Thread(future);
		thread.start();
	}

	public static <T> void run(final FutureTask<T> future, final long milliseconds) {
		if (future == null)
			return;

		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					Thread.sleep(milliseconds);
					future.run();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
	}

	public static <T> void run(final Callable<T> callable, final Ref<T> ref) {
		if (callable == null)
			return;
		ThreadUtils.run(new Runnable() {
			public void run() {
				try {
					ref.setValue(callable.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static <T> void run(final Callable<T> callable, final Ref<T> ref, final long milliseconds) {
		if (callable == null)
			return;
		ThreadUtils.run(new Runnable() {
			public void run() {
				try {
					Thread.sleep(milliseconds);
					ref.setValue(callable.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static <T> void run(final Callable<T> callable, final Callback<T> callback, final long milliseconds) {
		if (callable == null)
			return;
		new Thread(new Runnable() {
			public void run() {
				try {
					if (milliseconds > 0)
						Thread.sleep(milliseconds);
					T t = callable.call();
					if (callback != null) {
						callback.back(t);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void run(final Runnable runnable, final Runnable callback, final long milliseconds) {
		if (runnable == null)
			return;
		new Thread(new Runnable() {
			public void run() {
				if (milliseconds > 0) {
					try {
						Thread.sleep(milliseconds);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				runnable.run();
				if (callback != null)
					callback.run();
			}
		}).start();
	}
}
