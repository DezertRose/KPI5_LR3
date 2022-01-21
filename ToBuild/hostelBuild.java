package com.mycompany.app.ToBuild;

import com.mycompany.app.Objects.Hostel;

public class hostelBuild implements Builder{
    Hostel result;

    public void reset(){
        this.result = new Hostel();
    }

    @Override
    public void setId(int id) {
        this.result.id = id;
    }

    @Override
    public void setName(String name) {
        this.result.name = name;
    }

    @Override
    public void setNumbOfRoom(int numb) {
        this.result.numb_of_room = numb;
    }

    @Override
    public void setState(String state) {
        this.result.state = state;
    }

    @Override
    public void setFaculty(String faculty) {
        this.result.faculty = faculty;
    }

    @Override
    public void setLetters(String letters) {}

    @Override
    public void setNumbOfGroup(int numbOfGroup) {}

    @Override
    public void setHostel(Hostel host) { }

    @Override
    public void setPrivilege(int priv) {}

    @Override
    public void setDate(String date) {}

    @Override
    public void setMail(String mail) {}

    @Override
    public void setPassword(String password) {}

    @Override
    public void setStreet(String street) {
        this.result.street = street;
    }

    public Hostel getResult(){
        return this.result;
    }


}

