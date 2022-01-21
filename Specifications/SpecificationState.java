package com.mycompany.app.Specifications;

import com.mycompany.app.Objects.*;

public class SpecificationState extends SpecificationAbs<Students>{
    public String tempState;

    public SpecificationState(String temp){this.tempState = temp;}

    @Override
    public boolean isSatisfiedBy(Students temp) {
        return this.tempState.equals(temp.state);
    }
}
