package com.fattyca1.log;

import com.fattyca1.log.annotation.EnableLog;
import com.fattyca1.log.config.LogAdvisor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <br>注册Marker</br>
 *
 * @author fattyca1
 * @since 1.0
 */
public class LogHandlerRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        AtomicInteger i = new AtomicInteger();

        Map<String, Object> attributes = metadata.getAnnotationAttributes(EnableLog.class.getName());
        AnnotationAttributes[] logCfg = ((AnnotationAttributes) attributes).getAnnotationArray("value");
        Arrays.stream(logCfg).forEach(c -> registerLoHandler(registry, c, i.getAndIncrement()));
    }

    private void registerLoHandler(BeanDefinitionRegistry registry, AnnotationAttributes meta, int i) {
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(LogAdvisor.class);
        definition.addPropertyValue("logHeader", getLogHeader(meta));
        definition.addPropertyValue("web", getWeb(meta));
        definition.addPropertyValue("len", getLen(meta));
        definition.addPropertyValue("packages", getPackages(meta));
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
        String alias =  i + "LogAdvisor";
        AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
        registry.registerBeanDefinition(alias, beanDefinition);

//        BeanDefinitionHolder holder = new BeanDefinitionHolder(beanDefinition,
//                className, new String[]{alias});
//        BeanDefinitionReaderUtils.registerBeanDefinition(holder, registry);
    }


    private boolean getLogHeader(AnnotationAttributes attribute){
        return Boolean.parseBoolean(resolve(attribute.getString("logHeader")));
    }

    private boolean getWeb(AnnotationAttributes attribute){
        return Boolean.parseBoolean(resolve(attribute.getString("web")));
    }

    public int getLen(AnnotationAttributes attribute) {
        return Integer.parseInt(resolve(attribute.getString("len")));
    }

    public String[] getPackages(AnnotationAttributes attribute) {
        String[] packages = attribute.getStringArray("packages");
        // e.g: execution(public * com.*..*(..)) || execution(public * com.*..*(..))
        return Arrays.stream(packages).map(this::resolve)
                .filter(StringUtils::isNotEmpty)
                .toArray(String[]::new);
    }



    private String resolve(String value) {
        if (StringUtils.isNotEmpty(value)) {
            return this.environment.resolvePlaceholders(value);
        }
        return value;
    }
}
