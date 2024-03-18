package com.jvlang.housekeeping.statemachine;

import com.jvlang.housekeeping.util.AnnotationUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderStatusMapMaintainer {
    private static final Map<Integer, OrderStatus> map = new HashMap<>();

    static {
        List<Class<?>> classList = AnnotationUtil
                .getClassesWithAnnoUnderPackage
                        (OrderStatusInfo.class,
                                "com/jvlang/housekeeping/statemachine/status",
                                "/**/*.class");
        for (Class<?> clazz : classList) {
            OrderStatusInfo annotation = clazz.getAnnotation(OrderStatusInfo.class);
            try {
                map.put(annotation.id(), (OrderStatus) clazz.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static OrderStatus getOrderStatusById(Integer id){
        return map.get(id);
    }

}
