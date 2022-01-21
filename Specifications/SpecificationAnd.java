package com.mycompany.app.Specifications;

import java.util.HashSet;
import java.util.Set;

public class SpecificationAnd<T> extends SpecificationAbs<T>{
    private Set<Specification<T>> set = new HashSet<>();

    public SpecificationAnd(Specification<T> temp_a, Specification<T> temp_b){
        set.add(temp_a);
        set.add(temp_b);
    }

    @Override
    public boolean isSatisfiedBy(T temp) {
        for(Specification<T> st: set){
            if(!st.isSatisfiedBy(temp)){
                return false;
            }
        }
        return true;
    }

    @Override
    public SpecificationAbs<T> and(Specification<T> temp){
        set.add(temp);
        return this;
    }
}
