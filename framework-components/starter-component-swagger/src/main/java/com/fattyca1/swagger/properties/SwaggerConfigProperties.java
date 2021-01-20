package com.fattyca1.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * <br>swagger配置</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@Configuration
@ConfigurationProperties(prefix = SwaggerConfigProperties.PREFIX)
@Data
public class SwaggerConfigProperties {

    public static final String PREFIX = "fc.swagger";

    /**
     * 是否开启Swagger，默认关闭
     */
    private boolean enable = true;

    /**
     * 作者
     */
    private String author = "fattyca1";
    /**
     * 扫描的基础路径
     */
    private String basePackage = "com.fattyca1";
    /**
     * 作者邮箱地址
     */
    private String email;

    /**
     * 文档标题
     */
    private String title = "Swagger";

    /**
     * 文档描述
     */
    private String description = "Swagger Api";

    /**
     * 版本号
     */
    private String version = "1.0.0";

    /**
     * 官方地址
     */
    private String url = "";

    /**
     * license
     */
    private String license = "";

    /**
     * licenseUrl
     */
    private String licenseUrl = "";

    /**
     * 允许访问的路径
     */
    private List<String> allowPaths;

    /**
     * host
     */
    private String host;

    /**
     * 定义全局参数，
     */
    private List<GlobalParameter> globalOperationParameters;
}
