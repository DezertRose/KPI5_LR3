package com.mycompany.app.ToBuild;

import com.mycompany.app.Objects.Hostel;

import java.util.ArrayList;

public interface Builder {

    public void reset();

    public void setId(int id);
    public void setName(String name);
    public void setNumbOfRoom(int numb);
    public void setState(String state);
    public void setFaculty(String faculty);
    public void setLetters(String letters);
    public void setNumbOfGroup(int numbOfGroup);
    public void setHostel(Hostel host);
    public void setPrivilege(int priv);
    public void setDate(String date);
    public void setMail(String mail);
    public void setPassword(String password);
    public void setStreet(String street);
}
