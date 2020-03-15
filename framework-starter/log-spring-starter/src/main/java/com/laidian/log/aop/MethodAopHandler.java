package com.laidian.log.aop;

import com.laidian.erp.util.CollectionUtils;
import com.laidian.erp.util.JsonUtils;
import com.laidian.erp.util.StringUtils;
import com.laidian.erp.util.web.RequestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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

    private List<String> excludePackage;
    private boolean headPrint;
    private Integer argsLen;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {

        Method method = methodInvocation.getMethod();
        String methodName = method.getName();
        String className = method.getDeclaringClass().getSimpleName();

        long beginTime = System.currentTimeMillis();
        Object retVal = methodInvocation.proceed();
        long endTime = System.currentTimeMillis();

        boolean yes = whetherOutputFullLog(method.getDeclaringClass().getName());

        if (yes) {
            outputFullLog(methodName, className, Arrays.toString(methodInvocation.getArguments()), (endTime - beginTime), JsonUtils.obj2json(retVal));
        } else {
            outputPartLog(methodName, className, Arrays.toString(methodInvocation.getArguments()), (endTime - beginTime), JsonUtils.obj2json(retVal));
        }
        return retVal;

    }

    private void outputPartLog(String methodName, String className, String argsStr, long costTime, String retValStr) {
        log.info("method:[{}.{}]  take times[{}ms] arguments:{} return:{}", className, methodName, costTime, StringUtils.abbreviate(argsStr, argsLen), StringUtils.abbreviate(retValStr, argsLen));
    }

    private void outputFullLog(String methodName, String className, String args, long costTime, String retVal) {
        // 判断是不是前端请求
        Optional<HttpServletRequest> request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(req -> (ServletRequestAttributes) req)
                .map(ServletRequestAttributes::getRequest);

        String address = request.map(RequestUtils::getRemoteAddr).orElseGet(RequestUtils::getLocalAddress);
        String head = headPrint ? request.map(RequestUtils::obtainRequestHeadInfo).map(JsonUtils::obj2json).orElse("") : "";
        String requestUri = request.map(HttpServletRequest::getRequestURI).orElse("");
        log.info( headPrint ? "head:[{}] " : "{} " +  "address:[{}] method:[{}.{}] requestUrl:[{}] take times[{} ms] arguments:{} return:{}", headPrint ? head : "",
                address, className, methodName, requestUri, costTime, StringUtils.abbreviate(args, argsLen), StringUtils.abbreviate(retVal, argsLen));
    }

    private boolean whetherOutputFullLog(String fullClassName) {
        if (CollectionUtils.isEmpty(excludePackage)) {
            return true;
        }
        return excludePackage.stream().noneMatch(prefix -> StringUtils.startsWith(fullClassName, prefix));
    }


}
