package com.mycompany.app.ToBuild;

import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.Objects.User;

public class userBuild implements Builder{
    User result;

    public void reset(){
        this.result = new User();
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
    public void setState(String state) {}

    @Override
    public void setFaculty(String faculty) {
        this.result.faculty = faculty;
    }

    @Override
    public void setLetters(String letters) {}

    @Override
    public void setNumbOfGroup(int numbOfGroup) {}

    @Override
    public void setHostel(Hostel host) {
        this.result.hostel = host;
    }

    @Override
    public void setPrivilege(int priv) {}

    @Override
    public void setDate(String date) {}

    @Override
    public void setMail(String mail) {
        this.result.mail = mail;
    }

    @Override
    public void setPassword(String password) {
        this.result.password = password;
    }

    @Override
    public void setStreet(String street) {}

    ;

    public User getResult(){
        return this.result;
    };
}
