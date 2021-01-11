package com.fattyca1.log.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <br>注解配置</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(LogHandlerRegistrar.class)
public @interface LogConfig {
    /**
     * 是否打印头消息
     */
    boolean logHeader() default false;

    /**
     * 是否是web请求
     */
    boolean web() default false;

    /**
     * 日志长度
     */
    int len() default 1000;

    /**
     * 切入包
     */
    String[] packages() default {"com.fattyca1.*..*(..)"};
}
