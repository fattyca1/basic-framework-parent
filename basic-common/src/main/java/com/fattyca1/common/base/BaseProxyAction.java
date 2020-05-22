package com.fattyca1.common.base;

/**
 * <br>代理前后执行的方法</br>
 *
 * @author fattyca1
 */
public interface BaseProxyAction {

    /**
     * AOP，方法执行之前
     */
    void start();

    /**
     * AOP，方法执行之后
     */
    void stop();

}
