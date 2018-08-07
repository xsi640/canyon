package com.canyon.boot

import com.canyon.inject.*
import com.canyon.web.Controller
import org.apache.logging.log4j.LogManager

class ApplicationContext(
        vararg packages: String
) : Boot() {

    val logger = LogManager.getLogger()

    var basePackages = packages.toMutableList()
    var classScanner: ClassScanner = ClassScannerImpl()

    init {
        val beanFactory = BeanFactoryImpl()
        super.injectorContext = InjectorContextImpl(
                this.classScanner,
                DependenciesProcessorImpl(),
                beanFactory,
                this.basePackages
        )
        beanFactory.injectorContext = super.injectorContext
    }

    override fun run() {
        this.injectorContext.registAnnotation(Bean::class)
        this.injectorContext.registAnnotation(Controller::class)
        this.injectorContext.registSuperclass(Boot::class)
        this.injectorContext.excludedClass(ApplicationContext::class)
        this.injectorContext.initialize()

        this.injectorContext.getBeansFromSuper(Boot::class).forEach {
            it.injectorContext = super.injectorContext
            it.run()
        }
    }

    override fun destory() {

    }
}
