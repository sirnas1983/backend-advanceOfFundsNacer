package com.nacer.reportes.mapper;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ListMapper {

    public static  <T, R> List<R> mapListaToLista(List<T> lista, Function<T, R> mapperFunction) {
        return lista.stream()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }
}
