package com.mycompany.app.Specifications;

import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.Objects.Students;

public class SpecificationId extends SpecificationAbs<Students> {
    private int tempId;

    public SpecificationId(int temp){this.tempId = temp;}

    @Override
    public boolean isSatisfiedBy(Students temp) {
        return this.tempId == temp.id;
    }
}
