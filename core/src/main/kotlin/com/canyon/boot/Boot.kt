package com.canyon.boot

import com.canyon.inject.InjectorContext

abstract class Boot {
    /**
     * 依赖注入上下文
     */
    lateinit var injectorContext: InjectorContext

    /**
     * 排序
     */
    var order: Int = 0
    /**
     * 开始
     */
    abstract fun run()

    /**
     * 销毁
     */
    abstract fun destory()
}