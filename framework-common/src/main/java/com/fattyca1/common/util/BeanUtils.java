package com.fattyca1.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <br>springBeanUtils</br>
 *
 * @author fattyca1
 * @version 1.0
 * @date 2020/1/20 22:49
 * @since 1.0
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    /**
     * 拷贝List
     */
    public static <T> List<T> converter(List<?> source, Class<T> clazz) {
        return Optional.ofNullable(source).orElseGet(ArrayList::new)
            .stream()
            .map(o -> converter(o, clazz))
            .collect(Collectors.toList());
    }

    /**
     * 对象拷贝
     *
     * @param source 拷贝源
     * @param targetClazz 目标类型
     * @return 拷贝的对象
     */
    public static <T> T converter(Object source, Class<T> targetClazz) {
        try {
            if (Objects.isNull(source)) {
                return null;
            }
            T target = targetClazz.newInstance();
            copyProperties(source, target);
            return target;
        } catch (Throwable e) {
            throw new IllegalArgumentException("can not copyObject from " + source.getClass() + " to " + targetClazz);
        }
    }

}
