package me.kingtux.tuxmvc.simple;

import java.lang.reflect.Field;

public class    TMSUtils {

    public static Object getFieldValue(Object o, String fieldName) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object instanse, Object value, String fieldName) {
        try {
            Field field = instanse.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instanse, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
