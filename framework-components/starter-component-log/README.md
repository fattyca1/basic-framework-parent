## 使用方式

在启动类上添加注解 *@EnableLog* 注解即可.
> e.g: @EnableLog({@LogConfig(packages={"com.test..\*.\*(..)"})})

属性如下：
```
/**
 * 是否打印头消息 打印Request header详细
 */
String logHeader() default "false";

/**
 * 是否是web请求，web请求将打印address request uri
 */
String web() default "false";

/**
 * 日志长度 超过这个长度的日志将被截取
 */
String len() default "1000";

/**
 * 切入包路径 与spring切入点一样
 */
String[] packages() default {"com.fattyca1.*..*(..)"};
```

## 支持多路径

> e.g: @EnableLog({@LogConfig(packages={"com.test..\*.\*(..)"}), @LogConfig(packages = {"com.fattyca1.\*..\*.\*(..)"}, web = "true")})

根据不同的LogConfig配置，生成不同的bean对象

## todo
暂时不支持webflux.  因为使用的springWeb的RequestContextHolder获取request路径，webFlux还不支持，以后完善
