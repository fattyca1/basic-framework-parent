package com.fattyca1.log.config;

import com.fattyca1.log.aop.MethodAopHandler;
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

    private final boolean logHeader;
    private final boolean web;
    private final int len;
    private final String pointCut;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setAdvice(new MethodAopHandler(logHeader, web, len));
        this.setExpression(getPointcut(pointCut));
    }

    private String getPointcut(String pointCut) {
        return pointCut;
    }
}
