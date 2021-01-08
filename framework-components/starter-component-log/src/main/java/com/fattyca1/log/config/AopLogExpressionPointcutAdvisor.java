package com.fattyca1.log.config;

import com.fattyca1.log.aop.MethodAopHandler;
import com.fattyca1.log.properties.LogAopProperties;
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
public class AopLogExpressionPointcutAdvisor extends AspectJExpressionPointcutAdvisor implements InitializingBean{

    private final LogAopProperties prop;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setAdvice(new MethodAopHandler(prop.getExcludePackage(), prop.isHeadPrint(), prop.getArgsLen()));
        this.setExpression(prop.getExpression());
        log.info("【spring log component】 init successful ...");
    }
}
