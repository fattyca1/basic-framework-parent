package com.fattyca1.log.aop;

import com.fattyca1.common.util.JsonUtils;
import com.fattyca1.common.util.web.RequestUtils;
import com.fattyca1.log.properties.LogProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

/**
 * <br>service层日志</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/16
 * @since 1.0
 */
@Slf4j
@AllArgsConstructor
public class MethodAopHandler implements MethodInterceptor {

    private final LogProperties.LogConfig logConfig;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getSimpleName();

        long beginTime = System.currentTimeMillis();
        Object retVal = invocation.proceed();

        log(methodName, className, Arrays.toString(invocation.getArguments()), (System.currentTimeMillis() - beginTime), JsonUtils.toJson(retVal));
        return retVal;
    }

    private void log(String methodName, String className, String args, long costTime, String retVal) {
        // 判断是不是前端请求
        Optional<HttpServletRequest> request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(req -> (ServletRequestAttributes) req)
                .map(ServletRequestAttributes::getRequest);

        String address = request.map(RequestUtils::getRemoteAddr).orElseGet(RequestUtils::getLocalAddress);
        String head = logConfig.isLogHeader() ? request.map(RequestUtils::obtainRequestHeadInfo).map(JsonUtils::toJson).orElse("") : "";
        String requestUri = request.map(HttpServletRequest::getRequestURI).orElse("");

        log.info(logConfig.isLogHeader() ? "head:[{}] " : "{} "
                        + (logConfig.isWeb() ? "address:[{}] requestUrl:[{}] " : "{}{}")
                        + "method:[{}.{}]  cost times[{} ms] arguments:[{}] return:[{}]",
                logConfig.isLogHeader() ? head : "",
                logConfig.isWeb() ? address : "",
                logConfig.isWeb() ? requestUri : "",
                className, methodName, costTime,
                StringUtils.abbreviate(args, logConfig.getLen()),
                StringUtils.abbreviate(retVal, logConfig.getLen()));
    }
}
