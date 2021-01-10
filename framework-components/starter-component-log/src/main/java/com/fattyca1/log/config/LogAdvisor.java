package com.fattyca1.log.config;

import com.fattyca1.log.aop.MethodAopHandler;
import com.fattyca1.log.properties.LogProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

/**
 * <br>扩展</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/17
 * @since 1.0
 */
@RequiredArgsConstructor
@Slf4j
public class LogAdvisor extends AspectJExpressionPointcutAdvisor implements InitializingBean{

    private final LogProperties.LogConfig config;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setAdvice(new MethodAopHandler(config));
        this.setExpression(config.getPointCut());
        log.info("【Spring log component, CutPoint {}】 init successful ...", config.getPointCut());
    }
}
