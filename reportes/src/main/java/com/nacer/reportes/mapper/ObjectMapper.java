package com.nacer.reportes.mapper;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class ObjectMapper {

    public static <T, U> void mapFields(T source, U destination) {
        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] destinationFields = destination.getClass().getDeclaredFields();

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
}