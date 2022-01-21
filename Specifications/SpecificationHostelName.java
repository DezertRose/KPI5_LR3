package com.mycompany.app.Specifications;

import com.mycompany.app.Objects.User;

public class SpecificationHostelName extends SpecificationAbs<User>{
    private String temp_Name;

    public SpecificationHostelName(String temp){this.temp_Name = temp;}

    @Override
    public boolean isSatisfiedBy(User temp) {
        return this.temp_Name.equals(temp.hostel.name);
    }
}
