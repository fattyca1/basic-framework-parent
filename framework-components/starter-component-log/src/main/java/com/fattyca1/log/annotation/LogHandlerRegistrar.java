package com.fattyca1.log.annotation;

import com.fattyca1.log.config.LogAdvisor;
import com.fattyca1.log.properties.LogProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <br>注册日志</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Slf4j
@Component
//@EnableConfigurationProperties(LogProperties.class)
public class LogHandlerRegistrar implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

//    @Autowired
    private LogProperties logProp = new LogProperties();


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 注册 CustomizedBeanDefinitionRegistryPostProcessor
        int i = 0;
        List<LogProperties.LogConfig> config = logProp.getConfig();
        config.forEach(c -> registryBean(registry, c, i));
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AnnotationConfigApplicationContext factory = (AnnotationConfigApplicationContext) applicationContext.getParentBeanFactory();
    }

    private void registryBean(BeanDefinitionRegistry registry, LogProperties.LogConfig config, int index) {
        BeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(LogAdvisor.class).addConstructorArgValue(config).getBeanDefinition();
        String beanName = LogAdvisor.class.getName() + index;
        registry.registerBeanDefinition(beanName, definition);
        log.debug("Log Aop bean name:{} registry successful.", beanName);
    }
}
