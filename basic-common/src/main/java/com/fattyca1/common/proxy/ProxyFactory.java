package com.fattyca1.common.proxy;

import com.fattyca1.common.base.BaseProxyAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <br>给某一个对象生成代理对象</br>
 *
 * @author fattyca1
 */
@Slf4j
@RequiredArgsConstructor
public class ProxyFactory<PROXY_OBJECT> {

    private final BaseProxyAction action;
    private final PROXY_OBJECT obj;

    public PROXY_OBJECT getProxy() {
        return (PROXY_OBJECT) Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),new CustomizedInvocationHandler());
    }

    class CustomizedInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            action.start();
            Object ret = method.invoke(obj, args);
            action.stop();

            log.info(action.toString());
            return ret;
        }
    }

}
