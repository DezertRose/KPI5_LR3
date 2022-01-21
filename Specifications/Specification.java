package com.mycompany.app.Specifications;

public interface Specification<T> {

    public boolean isSatisfiedBy(T temp);
}
