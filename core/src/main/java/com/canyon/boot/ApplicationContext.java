package com.canyon.boot;

import com.canyon.commons.HardwareUtils;
import com.canyon.commons.IOUtils;
import com.canyon.commons.StringUtils;
import com.canyon.config.ConfigFactory;
import com.canyon.inject.*;
import com.canyon.inject.exceptions.InitializeException;
import com.canyon.inject.exceptions.NotFoundBeanException;
import com.canyon.scan.ClassScanner;
import com.canyon.scan.StandardClassScanner;
import com.canyon.web.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Bean(singleton = true)
public class ApplicationContext extends Boot {
    private Map<Class<?>, Object> preloadingClasses = new HashMap<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public ApplicationContext(String... packages) {
        showBanner();

        ClassScanner classScanner = new StandardClassScanner(packages);
        BeanFactory beanFactory = new StandardBeanFactory();
        super.injectorContext = new InjectContextImpl(
                classScanner,
                new StandardDependenciesProcessor(),
                beanFactory,
                packages
        );
        beanFactory.setInjectContext(super.injectorContext);
        preloadingClasses.put(InjectorContext.class, this.injectorContext);
        preloadingClasses.put(ApplicationContext.class, this);
        preloadingClasses.put(Config.class, ConfigFactory.config);
    }

    private void showBanner() {
        String path = this.getClass().getResource("/banner.txt").getPath();
        String text = IOUtils.readFileAllText(path);
        String banner = text.replace("{version}", ConfigFactory.config.getString("version"));
        logger.info(banner);
    }

    @Override
    public void run() {
        logger.info(StringUtils.format("Starting Application on {0} with PID {1}",
                HardwareUtils.getComputerName(),
                HardwareUtils.getPID()));
        initInject();
    }

    private void initInject() {
        logger.info("Preparing related components for dependency injection...");
        this.injectorContext.registAnnotation(Bean.class);
        this.injectorContext.registAnnotation(Controller.class);
        this.injectorContext.registInterface(InjectProvider.class);
        this.injectorContext.registSuperclass(Boot.class);

        for (Class<?> clazz : this.preloadingClasses.keySet()) {
            this.injectorContext.excludedClass(clazz);
            this.injectorContext.addBean(this.preloadingClasses.get(clazz));
        }

        this.injectorContext.initialize();
        logger.info("Dependency injection ready.");

        try {
            List<Boot> boots = this.injectorContext.getBeanFromSuper(Boot.class, "");
            boots.sort(new BootComparator());
            for (Boot boot : boots) {
                boot.injectorContext = super.injectorContext;
                boot.run();
            }
        } catch (InitializeException e) {
            e.printStackTrace();
        } catch (NotFoundBeanException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destory() {

    }

    public class BootComparator implements Comparator<Boot> {

        @Override
        public int compare(Boot o1, Boot o2) {
            if (o1.order > o2.order) {
                return 1;
            } else if (o1.order < o2.order) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
