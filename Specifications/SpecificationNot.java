package com.mycompany.app.Specifications;

public class SpecificationNot<T> extends SpecificationAbs<T> {
    private Specification<T> spec;

    public SpecificationNot(Specification<T> temp){this.spec = temp;}

    @Override
    public boolean isSatisfiedBy(T temp) {
        return !spec.isSatisfiedBy(temp);
    }
}
