package com.mycompany.app.Specifications;

public abstract class SpecificationAbs<T> implements Specification<T> {

    public abstract boolean isSatisfiedBy(T temp);

    public SpecificationAbs<T> and(Specification<T> temp){return new SpecificationAnd<T>(this, temp);}
    public SpecificationAbs<T> or(Specification<T> temp){return new SpecificationOr<T>(this, temp);}
    public SpecificationAbs<T> not(){return new SpecificationNot<T>(this);}
}
