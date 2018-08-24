package com.canyon.commons.engines;

/**
		* 循环引擎
		*/
public interface CircleEngine {

	/**
	 * 开始
	 */
	void start();

	/**
	 * 停止
	 */
	void stop();

	/**
	 * 是否已经开始
	 * @return
	 */
	boolean getIsStart();

	/**
	 * 检查时间（单位：秒）
	 * @return
	 */
	int getDetectSpanInSecs();

	/**
	 * 设置检查时间（单位：秒）
	 * @param detectSpanInSecs
	 */
	void setDetectSpanInSecs(int detectSpanInSecs);

	/**
	 * 获取最后异常
	 * @return
	 */
	Exception getLastException();
}
