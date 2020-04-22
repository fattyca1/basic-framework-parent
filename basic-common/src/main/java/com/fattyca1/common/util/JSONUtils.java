package com.fattyca1.common.util;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * <br>JSON工具类</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/21 10:24
 * @since 1.0
 */
@Slf4j
public class JSONUtils {

    /**
     * 初始化实例
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = DateUtils.YYYY_MM_DD_HH_MM_SS;


    static {
        initObjectMapper();
        registerModule();
    }

    /**
     * <br>构造函数私有化</br>
     */
    private JSONUtils() {
        throw new UnsupportedOperationException("illegal operate");
    }


    /**
     * <br>getObjectMapper(供外部调用OjbectMapper对象)</br>
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }


    /**
     * <br>对象转为json</br>
     *
     * @return java.lang.String
     */
    public static String obj2json(Object obj) {

        if (obj == null) {
            return null;
        }

        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            log.error("Parse object to json error", e);
        }

        return json;
    }

    /**
     * <br>getJsonValue(从json串中获取某key对应的值)</br>
     *
     * @param jsonSrc, jsonKey
     */
    public static String getJsonValue(String jsonSrc, String jsonKey) {

        if (StringUtils.isEmpty(jsonSrc) || StringUtils.isEmpty(jsonKey)) {
            return null;
        }

        JsonNode node = JSONUtils.json2obj(jsonSrc, JsonNode.class);
        if (null == node) {
            return null;
        }

        JsonNode dataNode = node.get(jsonKey);
        if (null == dataNode) {
            return null;
        }

        return dataNode.toString();
    }

    /**
     * <br>json2obj(将json串转为对象)</br>
     *
     * @param jsonStr, clazz
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz) {

        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }

        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (Exception e) {
            log.error("Parse json to object error", e);
        }

        return t;
    }


    /**
     * <br> obj2T(将一般对象转为泛型)</br>
     *
     * @param obj, clazz
     */
    public static <T> T obj2T(Object obj, Class<T> clazz) {

        if (null == obj) {
            return null;
        }

        T t = null;
        try {
            t = OBJECT_MAPPER.readValue(obj2json(obj), clazz);
        } catch (IOException e) {
            log.warn("Parse json to object error", e);
        }

        return t;
    }

    /**
     * <br>json2list( 将json串转为list对象)</br>
     *
     * @param jsonStr, clazz
     * @return java.util.List<T>
     */
    public static <T> List<T> json2list(String jsonStr, Class<T> clazz) {

        if (StringUtils.isEmpty(jsonStr)) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> list = null;
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionLikeType(List.class, Map.class);
            list = OBJECT_MAPPER.readValue(jsonStr, javaType);
        } catch (Exception e) {
            log.error("Parse json to list error", e);
        }

        return Optional.ofNullable(list)
                .map(o -> o.stream().map(t -> map2obj(t, clazz)).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
    }

    /**
     * <br>Map转换成obj对象</br>
     *
     * @param clazz 类型
     * @return T
     */
    private static <T> T map2obj(@SuppressWarnings("rawtypes") Map map, Class<T> clazz) {

        return OBJECT_MAPPER.convertValue(map, clazz);
    }


    /**
     * <br>Obj转换成map对象</br>
     *
     * @return T
     */
    public static Map pojo2map(Object obj) {

        return OBJECT_MAPPER.convertValue(obj, Map.class);
    }


    private static void registerModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateUtils.YYYY_MM_DD_HH_MM_SS_FORMAT));
        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateUtils.YYYY_MM_DD_FORMAT));
        module.addSerializer(LocalTime.class, new LocalTimeSerializer(DateUtils.HH_MM_SS_FORMAT));

        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateUtils.YYYY_MM_DD_HH_MM_SS_FORMAT));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateUtils.YYYY_MM_DD_FORMAT));
        module.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateUtils.HH_MM_SS_FORMAT));
        module.addSerializer(Long.class, ToStringSerializer.instance);

        OBJECT_MAPPER.registerModule(new JavaTimeModule());
        OBJECT_MAPPER.registerModule(module);
    }

    private static void initObjectMapper() {
        // 对象字段全部列入
        OBJECT_MAPPER.setSerializationInclusion(NON_DEFAULT);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        // 统一日期格式yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
        // 忽略在json字符串中存在,但是在java对象中不存在对应属性的情况
        OBJECT_MAPPER.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        //不显示为null的字段
        OBJECT_MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 过滤对象的null属性.
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略transient
        OBJECT_MAPPER.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
    }
}
