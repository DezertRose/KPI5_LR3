package com.mycompany.app.Specifications;

import java.util.HashSet;
import java.util.Set;

public class SpecificationOr<T> extends SpecificationAbs<T> {

    private Set<Specification<T>> set = new HashSet<>();

    public SpecificationOr(Specification<T> temp_a, Specification<T> temp_b){
        set.add(temp_a);
        set.add(temp_b);
    }

    @Override
    public boolean isSatisfiedBy(T temp) {
        for(Specification<T> st: set){
            if(st.isSatisfiedBy(temp)){
                return true;
            }
        }
        return false;
    }

    @Override
    public SpecificationAbs<T> or(Specification<T> temp){
        set.add(temp);
        return this;
    }
}
