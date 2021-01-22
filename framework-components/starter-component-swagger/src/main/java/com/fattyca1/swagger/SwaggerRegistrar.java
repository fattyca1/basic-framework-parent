package com.fattyca1.swagger;

import com.fattyca1.swagger.config.SwaggerConfiguration;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * <br>swagger开启</br>
 *
 * @author fattyca1
 * @since 1.0
 */
public class SwaggerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        registry.registerBeanDefinition(SwaggerConfiguration.Marker.class.getName(), new RootBeanDefinition(SwaggerConfiguration.Marker.class));
    }
}
