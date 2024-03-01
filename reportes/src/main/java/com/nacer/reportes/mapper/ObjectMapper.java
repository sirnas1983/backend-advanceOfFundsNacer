package com.nacer.reportes.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapper {

    public static <T, U> void mapFields(T source, U destination) {
        if (source == null || destination == null) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> destinationClass = destination.getClass();

        List<Field> sourceFields = getAllFields(sourceClass);
        List<Field> destinationFields = getAllFields(destinationClass);

        for (Field sourceField : sourceFields) {
            for (Field destinationField : destinationFields) {
                if (sourceField.getName().equals(destinationField.getName())) {
                    try {
                        sourceField.setAccessible(true);
                        Object value = sourceField.get(source);
                        if (value != null) {
                            destinationField.setAccessible(true);
                            destinationField.set(destination, value);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    private static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return fields;
    }
}