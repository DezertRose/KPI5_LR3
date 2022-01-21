package com.mycompany.app.Objects;

public class Hostel {
    public int id;
    public String name;
    public String street;
    public int numb_of_room;
    public String state;
    public String faculty;

    @Override
    public String toString() {
        return "Hostel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street='" + street + '\'' +
                ", numb_of_room=" + numb_of_room +
                ", state='" + state + '\'' +
                ", faculty='" + faculty + '\'' +
                '}';
    }
}
