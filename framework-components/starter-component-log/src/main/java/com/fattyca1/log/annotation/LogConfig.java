package com.fattyca1.log.annotation;

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
public @interface LogConfig {
    /**
     * 是否打印头消息
     */
    String logHeader() default "false";

    /**
     * 是否是web请求
     */
    String web() default "false";

    /**
     * 日志长度
     */
    String len() default "1000";

    /**
     * 切入包
     */
    String[] packages() default {"com.fattyca1.*..*.*(..)"};
}
