package com.nacer.reportes.mapper;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListMapper {

    public static  <T, R> List<R> mapListaToLista(List<T> lista, Function<T, R> mapperFunction) {
        return lista.stream()
                .map(mapperFunction)
                .collect(Collectors.toList());
    }
}
