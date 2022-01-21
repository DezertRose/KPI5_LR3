package com.mycompany.app;

import com.mycompany.app.Hendler.Support;
import com.mycompany.app.Objects.*;
import com.mycompany.app.ToBuild.Data;
import com.mycompany.app.ToBuild.*;

import java.util.ArrayList;
import java.util.Random;

public class InsertIntoDB {

    public static String createRandomString(int length){
        Random r = new Random();

        return r.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static void createNewUser(int numb, ArrayList<User> userArr, ArrayList<Hostel> hostelArr){
        userBuild ub = new userBuild();
        Data bData = new Data(ub);
        Random r = new Random();

        for(int j = 0; j<numb; j++) {

            bData.insideData.hostel = Facade.HostelsData.getHostelById(r.nextInt(12)+1, hostelArr);
            bData.insideData.mail = createRandomString(r.nextInt(10)+1)+"@gmail.com";
            bData.insideData.password = createRandomString(r.nextInt(25)+1);

            bData.insert("user");
            User newUser = ub.getResult();

            Request re = new Request("INSERT INTO FACULTY (name)\n" +
                    "SELECT * \n" +
                    "FROM (SELECT '" + newUser.faculty + "' as temp) as temt\n" +
                    "WHERE NOT EXISTS (\n" +
                    "    SELECT name FROM FACULTY WHERE name = '" + newUser.faculty + "'\n" +
                    ")\n" +
                    "insert into \"USER\"\n" +
                    "values("+r.nextInt(7)+1+", " + newUser.hostel.id + ", '" + newUser.mail + "', '" + newUser.password + "');");
            String response = (String) re.exec();
            //Facade.allUsersSecond = Facade.HostelsData.setUserData(Facade.allHostelsSecond);
        }
    }

    public static void createNewStudent(int numb){
        studentsBuild stdb = new studentsBuild();
        Data bData = new Data(stdb);
        Random r = new Random();

        int temp = r.nextInt(30)+1990;

        for(int j = 0; j<numb; j++) {

            bData.insideData.date = temp +"-"+ (r.nextInt(12) + 1) +"-"+ (r.nextInt(28) + 1);
            if(r.nextInt(10)<5){
                bData.insideData.state = "F";
            }
            else{
                bData.insideData.state = "M";
            }

            bData.insert("student");
            Students newUser = stdb.getResult();

            String test = "insert into STUDENT\n" +
                    "values("+(r.nextInt(10)+1)+", " + (r.nextInt(5)+1) + ", " + (r.nextInt(12)+1)
                    + ", " + (r.nextInt(5)+1) + ", '" + newUser.state + "', '" + newUser.date + "');";

            //System.out.println(test);

            RequestThird re = new RequestThird(test);
            String response = (String) re.exec();
            //Facade.allUsersSecond = Facade.HostelsData.setUserData(Facade.allHostelsSecond);
        }
    }
}

