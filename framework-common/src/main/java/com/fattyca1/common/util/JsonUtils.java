package com.fattyca1.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

/**
 * <br>JSON工具类</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/21 10:24
 * @since 1.0
 */
@Slf4j
public class JsonUtils {

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
    private JsonUtils() {
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
    public static String toJson(Object obj) {
        return Optional.ofNullable(obj).map(o -> {
            try {
                return OBJECT_MAPPER.writeValueAsString(obj);
            } catch (JsonProcessingException e) {
                log.error("parser error", e);
            }
            return null;
        }).orElse(null);
    }

    /**
     * <br>getJsonValue(从json串中获取某key对应的值)</br>
     *
     * @param jsonSrc, jsonKey
     */
    public static String getValue(String jsonSrc, String jsonKey) {
        return Optional.ofNullable(JsonUtils.toObj(jsonSrc, JsonNode.class))
                .map(n -> n.get(jsonKey))
                .map(JsonNode::toString)
                .orElse(null);

    }

    /**
     * <br>json2obj(将json串转为对象)</br>
     *
     * @param jsonStr, clazz
     */
    public static <T> T toObj(String jsonStr, Class<T> clazz) {
        return convert(jsonStr, clazz, OBJECT_MAPPER::readValue);
    }

    /**
     * <br>json2obj(将json串转为对象)</br>
     *
     * @param jsonStr, clazz
     */
    public static <T> T toObj(String jsonStr, TypeReference<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.error("parse json error. {}", jsonStr);
        }

        return null;
    }

    /**
     * <br>Map转换成obj对象</br>
     *
     * @param clazz 类型
     * @return T
     */
    private static <T> T toObj(Object o, Class<T> clazz) {
        return JsonUtils.toObj(toJson(o), clazz);
    }


    @FunctionalInterface
    interface BiFunctionWithException<T,U,R>{
        R apply(T t, U u) throws Exception;
    }

    private static <O,T> T convert(O object, Class<T> c1, BiFunctionWithException<O ,Class<T>, T> biFunction) {

        if (Objects.isNull(object)) {
            log.info("Parser object is null");
            return null;
        }

        try {
            return biFunction.apply(object, c1);
        } catch (Exception e) {
            log.error("Parse json to object error", e);
        }

        return null;
    }

    /**
     * <br>json2list( 将json串转为list对象)</br>
     *
     * @param jsonStr, clazz
     * @return java.util.List<T>
     */
    public static <T> List<T> toList(String jsonStr, Class<T> clazz) {

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
                .map(o -> o.stream().map(t -> toObj(t, clazz)).collect(Collectors.toList()))
                .orElseGet(ArrayList::new);
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
