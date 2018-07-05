package com.canyon.boot

import com.canyon.inject.*

class ApplicationContext(
        vararg packages: String
) : Boot {

    var basePackages = packages.toMutableList()
    var classScanner: ClassScanner = ClassScannerImpl()
    var injectorContext: InjectorContext

    init {
        var beanFactory = BeanFactoryImpl()
        this.injectorContext = InjectorContextImpl(
                this.classScanner,
                DependenciesProcessorImpl(),
                beanFactory,
                this.basePackages
        )
        beanFactory.injectorContext = this.injectorContext
    }

    override fun run() {
        this.injectorContext.registAnnotation(Bean::class)
        this.injectorContext.registInterface(Boot::class)
        this.injectorContext.excludedClass(ApplicationContext::class)
        this.injectorContext.initialize()

        this.injectorContext.getBeanFromSuper(Boot::class).forEach {
            it.run()
        }
    }

    override fun destory() {

    }

}
