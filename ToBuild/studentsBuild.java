package com.mycompany.app.ToBuild;

import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.Objects.Students;

public class studentsBuild implements Builder{
    Students result;

    public void reset(){
        this.result = new Students();
    }

    @Override
    public void setId(int id) {
        this.result.id = id;
    }

    @Override
    public void setName(String name) {}

    @Override
    public void setNumbOfRoom(int numb) {}

    @Override
    public void setState(String state) {
        this.result.state = state;
    }

    @Override
    public void setFaculty(String faculty) {
        this.result.faculty = faculty;
    }

    @Override
    public void setLetters(String letters) {
        this.result.letters = letters;
    }

    @Override
    public void setNumbOfGroup(int numbOfGroup) {
        this.result.numb_of_group = numbOfGroup;
    }

    @Override
    public void setHostel(Hostel host) {
        this.result.hostel = host;
    }

    @Override
    public void setPrivilege(int priv) {}

    @Override
    public void setDate(String date) {
        this.result.date = date;
    }

    @Override
    public void setMail(String mail) {}

    @Override
    public void setPassword(String password) {}

    @Override
    public void setStreet(String street) {}

    public Students getResult(){
        return this.result;
    }


}
