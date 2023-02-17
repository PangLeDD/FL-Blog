package com.lei.mapper.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils(){};

    public static <V> V copyBean(Object source, Class<V> clazz) {

        V instance = null;
        try {
            instance = clazz.newInstance();
            BeanUtils.copyProperties(source, instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static <V> List<V> copyBeanList(List<? extends Object> source,Class<V> clazz){

        return source.stream()
                .map(O -> copyBean(O, clazz))
                .collect(Collectors.toList());
    }
}
