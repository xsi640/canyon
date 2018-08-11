package com.canyon.boot

import com.canyon.commons.HardwareUtils
import com.canyon.config.ConfigFactory
import com.canyon.inject.*
import com.canyon.web.Controller
import com.typesafe.config.Config
import org.apache.logging.log4j.LogManager
import kotlin.reflect.KClass

@Bean(singleton = true)
class ApplicationContext(
        vararg packages: String
) : Boot() {
    private var basePackages = packages.toMutableList()
    private var classScanner: ClassScanner = ClassScannerImpl()

    private val preloadingClasses = mutableMapOf<KClass<*>, Any>()

    private val logger = LogManager.getLogger()

    init {
        showBanner()
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
        preloadingClasses += Config::class to ConfigFactory.config
    }

    override fun run() {
        logger.info("Starting Application on ${HardwareUtils.getComputerName()} with PID ${HardwareUtils.getPID()}")
        initInject()
    }

    override fun destory() {

    }

    private fun initInject() {
        logger.info("Preparing related components for dependency injection...")
        this.injectorContext.registAnnotation(Bean::class)
        this.injectorContext.registAnnotation(Controller::class)
        this.injectorContext.registInterface(InjectProvider::class)
        this.injectorContext.registSuperclass(Boot::class)

        this.preloadingClasses.forEach { t, u ->
            this.injectorContext.excludedClass(t)
            this.injectorContext.addBean(u)
        }

        this.injectorContext.initialize()
        logger.info("Dependency injection ready.")

        this.injectorContext.getBeansFromSuper(Boot::class).sortedBy { it.order }.forEach {
            it.injectorContext = super.injectorContext
            it.run()
        }
    }

    private fun showBanner() {
        val banner = javaClass.getResource("/banner.txt").readText().replace("{version}", ConfigFactory.config.getString("version"))
        logger.info(banner)
    }
}
