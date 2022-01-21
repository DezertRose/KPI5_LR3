package com.mycompany.app.ToBuild;
import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.ToBuild.Builder;

public class Data {
    public insideData insideData;
    Builder builder;

    public Data(Builder builder){
        this.builder = builder;
        insideData = new insideData();
    }

    public void changeBuilder(Builder builder){
        this.builder = builder;
    }

    public class insideData{
        public int id = 0;
        public String name = null;
        public int numbOfRooms = 0;
        public String state = null;
        public String faculty = null;
        public String letters = null;
        public int numbOfGroup = 0;
        public Hostel hostel = null;
        public int priv = 0;
        public String date = null;
        public String mail = null;
        public String password = null;
        public String street = null;

    }

    public void insert( String type){
        builder.reset();

        builder.setId(this.insideData.id);
        builder.setDate(this.insideData.date);
        builder.setFaculty(this.insideData.faculty);
        builder.setHostel(this.insideData.hostel);
        builder.setLetters(this.insideData.letters);
        builder.setMail(this.insideData.mail);
        builder.setName(this.insideData.name);
        builder.setNumbOfGroup(this.insideData.numbOfGroup);
        builder.setNumbOfRoom(this.insideData.numbOfGroup);
        builder.setPassword(this.insideData.password);
        builder.setPrivilege(this.insideData.priv);
        builder.setState(this.insideData.state);
        builder.setStreet(this.insideData.street);
    }

}
