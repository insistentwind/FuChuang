package com.ning.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


public class BeanCopyUtils {

    private BeanCopyUtils(){

    }
    //实现单个拷贝
    public static <T> T copyBean(Object source,Class<T> clazz) {
        //创建目标对象
        T result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    //实现遍历拷贝
    public static<V,T> List<T> copyBeanList(List<V> list, Class<T> clazz){
        return list.stream().map
                (o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
