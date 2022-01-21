package com.mycompany.app.Objects;

public class User {
    public int id;
    public String faculty;
    public Hostel hostel;
    public String mail;
    public String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", faculty='" + faculty + '\'' +
                ", hostel=" + hostel +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
