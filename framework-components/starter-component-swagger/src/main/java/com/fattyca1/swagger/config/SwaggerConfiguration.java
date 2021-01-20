package com.fattyca1.swagger.config;

import com.fasterxml.classmate.TypeResolver;
import com.fattyca1.swagger.properties.GlobalParameter;
import com.fattyca1.swagger.properties.SwaggerConfigProperties;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <br>swagger配置</br>
 *
 * @author fattyca1
 * @since 1.0
 */
@EnableSwagger2
@Configuration
@EnableConfigurationProperties(SwaggerConfigProperties.class)
@Import({springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration.class})
@ConditionalOnBean(SwaggerConfiguration.Marker.class)
public class SwaggerConfiguration {

    public static class Marker{
    }

    private final Logger logger = LoggerFactory.getLogger(SwaggerConfiguration.class);

    @Autowired
    private TypeResolver typeResolver;

    @Autowired
    private SwaggerConfigProperties swaggerConfigProperties;

    @Bean
    public Docket petApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                //扫描的包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerConfigProperties.getBasePackage()))
                //扫描的URL
                .paths(paths())
                .build()
                .pathMapping("/")
                //类型的转化
                .directModelSubstitute(LocalDate.class,String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(DeferredResult.class,
                        typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                        typeResolver.resolve(WildcardType.class)))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.POST,
                        Lists.newArrayList(new ResponseMessageBuilder()
                                .code(200)
                                .responseModel(new ModelRef("ResponseEntity"))
                                .build()))
                .enableUrlTemplating(false)
                .apiInfo(apiInfo())
                .tags(new Tag("Pet Service", "All apis relating to pets"));

        //更改请求的host
        if(StringUtils.isNotEmpty(swaggerConfigProperties.getHost())){
            docket.host(swaggerConfigProperties.getHost());
        }
        //为所有操作添加默认参数
        if(CollectionUtils.isNotEmpty(swaggerConfigProperties.getGlobalOperationParameters())){
            List<Parameter> parameters = new ArrayList<>(swaggerConfigProperties.getGlobalOperationParameters().size());
            for (GlobalParameter globalParameter : swaggerConfigProperties.getGlobalOperationParameters()) {
                parameters.add(new ParameterBuilder()
                        .name(globalParameter.getName())
                        .description(globalParameter.getDescription())
                        .modelRef(new ModelRef(globalParameter.getModelRef()))
                        .parameterType(globalParameter.getParameterType())
                        .required(globalParameter.isRequire())
                        .defaultValue(globalParameter.getDefaultValue())
                        .build());
            }
            docket.globalOperationParameters(parameters);
        }
        logger.info("【Spring boot component】 【swagger】 init successful!");
        return docket;
    }

    private Predicate<String> paths() {
        if(CollectionUtils.isNotEmpty(swaggerConfigProperties.getAllowPaths())){
            return Predicates.or(swaggerConfigProperties.getAllowPaths().stream().map(PathSelectors::regex).toArray(Predicate[]::new));
        }else{
            return PathSelectors.any();
        }
    }

    /**
     * API配置
     *
     * @return
     */
    private ApiInfo apiInfo() {
        Contact contact = new Contact(swaggerConfigProperties.getAuthor(), "", swaggerConfigProperties.getEmail());
        //大标题 title
        return new ApiInfo(swaggerConfigProperties.getTitle(),
                //小标题
                swaggerConfigProperties.getDescription(),
                //版本
                swaggerConfigProperties.getVersion(),
                //termsOfServiceUrl
                swaggerConfigProperties.getUrl(),
                //作者
                contact,
                //链接显示文字
                swaggerConfigProperties.getLicense(),
                //网站链接
                swaggerConfigProperties.getLicenseUrl(),
                Collections.emptyList()
        );
    }

    /**
     * swagger-ui配置
     *
     * @return
     */
    @Bean
    public UiConfiguration uiConfig() {
        return new UiConfiguration(null, "none", "alpha",
                "schema", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false,
                true, 60000L);
    }
}
