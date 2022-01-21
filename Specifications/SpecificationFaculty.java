package com.mycompany.app.Specifications;

import com.mycompany.app.Objects.Hostel;

public class SpecificationFaculty extends SpecificationAbs<Hostel> {
    private String tempFaculty;

    public SpecificationFaculty(String temp){this.tempFaculty = temp;}

    @Override
    public boolean isSatisfiedBy(Hostel temp) {
        return this.tempFaculty.equals(temp.faculty);
    }
}
