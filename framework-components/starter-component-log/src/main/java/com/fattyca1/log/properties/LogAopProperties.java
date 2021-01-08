package com.fattyca1.log.properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * <br>属性配置</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/2/16
 * @since 1.0
 */
@ConfigurationProperties(prefix = LogAopProperties.PREFIX)
public class LogAopProperties {

    public static final String PREFIX = "laidian.log";

    // 默认切入点， 采用dubbo方式，不开启controller
//    private final String DEFAULT_EXPRESSION = "execution(public * com.fattyca1.*.service.*.*(..)) || " +
//            "execution(public * com.fattyca1.*.controller.*.*(..)) || " +
//            "execution(public * com.fattyca1.*.mapper.*.*(..)) ";

    private final int ARGS_LENTH = 100;

    private final String MYBATIS_PLUS_PACKAGE = "com.baomidou.mybatisplus";

    /**
     * 切入点
     */
    private List<String> expressions;

    /**
     * 前端调用打印头消息
     */
    private boolean headPrint;

    /**
     * 入参长度 (文件特别长要注意)
     */
    private Integer argsLen;

    /**
     * 不打印地址,uri, head信息 （mapper 和 service  不用打印地址/uri）
     */
    private List<String> excludePackage;


    public List<String> getExcludePackage() {
        return Optional.ofNullable(excludePackage).map(ep -> {
            ep.add(MYBATIS_PLUS_PACKAGE);
            return ep;
        }).orElseGet(() -> Collections.singletonList(MYBATIS_PLUS_PACKAGE));
    }

    public void setExcludePackage(List<String> excludePackage) {
        this.excludePackage = excludePackage;
    }

    public String getExpression() {
        return StringUtils.join(expressions, " || ");
    }


    public List<String> getExpressions() {
        return expressions;
    }

    public void setExpressions(List<String> expressions) {
        this.expressions = expressions;
    }

    public boolean isHeadPrint() {
        return headPrint;
    }

    public void setHeadPrint(boolean headPrint) {
        this.headPrint = headPrint;
    }

    public Integer getArgsLen() {
        return (argsLen != null && argsLen > 0) ? argsLen : ARGS_LENTH;
    }

    public void setArgsLen(Integer argsLen) {
        this.argsLen = argsLen;
    }
}
