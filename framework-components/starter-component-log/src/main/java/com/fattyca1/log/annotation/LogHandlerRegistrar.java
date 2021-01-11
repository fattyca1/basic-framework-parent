package com.fattyca1.log.annotation;

import com.fattyca1.log.LogHandlerAutoConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;

/**
 * <br>注册Marker</br>
 *
 * @author fattyca1
 * @since 1.0
 */
public class LogHandlerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {


    private ResourceLoader resourceLoader;

    private Environment environment;


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableLog.class.getName());

        AnnotationAttributes[] logCfg = ((AnnotationAttributes) attributes).getAnnotationArray("log");

        Arrays.stream(logCfg).forEach(c -> {
            registerBeanDefinition(registry, c);
        });


        AbstractBeanDefinition definition = BeanDefinitionBuilder.rootBeanDefinition(LogHandlerAutoConfiguration.Marker.class).getBeanDefinition();
        registry.registerBeanDefinition(LogHandlerAutoConfiguration.Marker.class.getName(), definition);
    }

    private void registerBeanDefinition(BeanDefinitionRegistry registry, AnnotationAttributes meta) {
        int len = meta.getNumber("len").intValue();
        boolean web = meta.getBoolean("web");
        boolean logHeader = meta.getBoolean("logHeader");
        meta.getString("packages");


    }

    public boolean getLogHeader(AnnotationAttributes attributes){
        return false;
    }

    private String resolve(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }
}
