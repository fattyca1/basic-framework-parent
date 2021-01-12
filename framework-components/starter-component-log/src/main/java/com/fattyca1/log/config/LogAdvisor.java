package com.fattyca1.log.config;

import com.fattyca1.log.aop.MethodAopHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <br>扩展</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/17
 * @since 1.0
 */
@Slf4j
@Data
public class LogAdvisor extends AspectJExpressionPointcutAdvisor implements InitializingBean {

    private boolean logHeader;
    private boolean web;
    private int len;
    private String[] packages;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.setAdvice(new MethodAopHandler(logHeader, web, len));
        this.setExpression(getPointCut(packages));
    }

    public String getPointCut(String[] packages){
        if (ArrayUtils.isEmpty(packages)) {
            return "execution(public * com.fattyca1..*.*(..))";
        }

        return Arrays.stream(packages).filter(StringUtils::isNotEmpty)
                .map(s -> "execution(public * " + s + ")")
                .collect(Collectors.joining("||"));
    }
}
