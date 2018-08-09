package com.canyon.boot

import com.canyon.inject.*
import com.canyon.web.Controller
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass

@Bean(singleton = true)
class ApplicationContext(
        vararg packages: String
) : Boot() {
    private var basePackages = packages.toMutableList()
    private var classScanner: ClassScanner = ClassScannerImpl()

    private val preloadingClasses = mutableMapOf<KClass<*>, Any>()

    init {
        val beanFactory = BeanFactoryImpl()
        super.injectorContext = InjectorContextImpl(
                this.classScanner,
                DependenciesProcessorImpl(),
                beanFactory,
                this.basePackages
        )
        beanFactory.injectorContext = super.injectorContext

        preloadingClasses += InjectorContext::class to this.injectorContext
        preloadingClasses += ApplicationContext::class to this
    }

    override fun run() {
        this.injectorContext.registAnnotation(Bean::class)
        this.injectorContext.registAnnotation(Controller::class)
        this.injectorContext.registSuperclass(Boot::class)

        this.preloadingClasses.forEach { t, u ->
            this.injectorContext.excludedClass(t)
            this.injectorContext.addBean(u)
        }

        this.injectorContext.initialize()

        this.injectorContext.getBeansFromSuper(Boot::class).forEach {
            it.injectorContext = super.injectorContext
            it.run()
        }
    }

    override fun destory() {

    }
}
