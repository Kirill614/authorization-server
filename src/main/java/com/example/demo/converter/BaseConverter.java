package com.example.demo.converter;

import java.util.function.Function;

public class BaseConverter <E, D> {
    private final Function<E, D> function1;
    private final Function<D, E> function2;

    public BaseConverter(Function<E, D> function1, Function<D, E> function2){
        this.function1 = function1;
        this.function2 = function2;
    }

    public D convertFromEntity(E entity){
        return function1.apply(entity);
    }

    public E convertFromDto(D dto){
        return function2.apply(dto);
    }
}
