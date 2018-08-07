package com.canyon.commons.engines

import com.canyon.commons.ThreadUtils


class StandardCircleEngine(var runnable: Runnable) : CircleEngine, Runnable {

    override var isStart = false
        private set
    private var stopping = false
    override var lastException: Exception? = null
        private set
    override var detectSpanInSecs = 10

    override fun start() {
        ThreadUtils.run(this)
        this.isStart = true
    }

    override fun stop() {
        this.stopping = true
        this.isStart = false
    }

    override fun run() {
        while (true) {
            try {
                this.runnable.run()
                Thread.sleep(this.detectSpanInSecs.toLong())
                if (this.stopping)
                    break
            } catch (ex: Exception) {
                this.lastException = ex
            }

        }
        this.stopping = false
    }

}
