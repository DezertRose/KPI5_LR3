package com.mycompany.app.Hendler;

import com.mycompany.app.Facade;
import com.mycompany.app.Objects.*;
import com.mycompany.app.Request;
import com.mycompany.app.RequestThird;
import com.mycompany.app.Specifications.*;
import com.mycompany.app.ToBuild.*;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

public class CrudData {

    public static ArrayList<Hostel> getHostelByFaculty(String req, ArrayList<Hostel> hostArr){
        ArrayList<Hostel> result = new ArrayList<>();
        try{
            Specification<Hostel> hostSpec = new SpecificationFaculty(req);
            result = useSpecification(hostArr, hostSpec);
            return result;
        }
        catch (Exception ex){
            System.out.println("Can't find hostels in DB!");
        }
        return result;
    }

    public static ArrayList<User> getUserByHostelName(String req, ArrayList<User> userArr){
        ArrayList<User> result = new ArrayList<>();
        try{
            Specification<User> userSpec = new SpecificationHostelName(req);
            result = useSpecification(userArr, userSpec);
            return result;
        }
        catch (Exception ex){
            System.out.println("Can't find user by hostel name in DB!");
        }
        return result;
    }

    public static ArrayList<Students> getStudentByState(String req, ArrayList<Students> stdArr){
        ArrayList<Students> result = new ArrayList<>();
        try{
            Specification<Students> studSpec = new SpecificationState(req);
            result = useSpecification(stdArr, studSpec);
            return result;
        }
        catch (Exception ex){
            System.out.println("Can't find hostels in DB!");
        }
        return result;
    }

    static <T> ArrayList<T> useSpecification(ArrayList<T> tempArr, Specification<T> specification){
        ArrayList<T> tempData = new ArrayList<>();

        for(T temp: tempArr){
            if (specification.isSatisfiedBy(temp)){
                tempData.add(temp);
            }
        }
        return tempData;
    }

    public static ArrayList<Students> getStudentById(String req, ArrayList<Students> stdArr){
        ArrayList<Students> result = new ArrayList<>();
        int tempId = Integer.parseInt(req);
        try{
            Specification<Students> studSpec = new SpecificationId(tempId);
            result = useSpecification(stdArr, studSpec);
            return result;
        }
        catch (Exception ex){
            System.out.println("Can't find hostels in DB!");
        }
        return result;

    }

    public static String createNewUser(String query, ArrayList<User> userArr, ArrayList<Hostel> hostelArr){
        Map<String,Object> data = Support.getFromJSON(query);
        userBuild ub = new userBuild();
        Data bData = new Data(ub);

        bData.insideData.faculty = (String)data.get("faculty");
        bData.insideData.hostel = Facade.HostelsData.getHostelById((Integer)data.get("hostel_id"), hostelArr);
        bData.insideData.mail = (String)data.get("mail");
        bData.insideData.password = (String)data.get("password");
        bData.insert("user");
        User newUser = ub.getResult();

        userArr.add(newUser);
        Request re = new Request("INSERT INTO FACULTY (name)\n" +
                "SELECT * \n" +
                "FROM (SELECT '"+newUser.faculty+"' as temp) as temt\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT name FROM FACULTY WHERE name = '"+newUser.faculty+"'\n" +
                ")\n" +
                "insert into \"USER\"\n" +
                "values((select FACULTY_ID \n" +
                "\t\tfrom FACULTY \n" +
                "\t\twhere (FACULTY.NAME ='"+newUser.faculty+"')), "+newUser.hostel.id+", '"+newUser.mail+"', '"+newUser.password+"');");
        String response = (String)re.exec();
        Facade.allUsers = Facade.HostelsData.setUserData(Facade.allHostels);
        return response;
    }

    public static String updateHostel(String query, ArrayList<Hostel> hostArr){
        Map<String,Object>res = Support.getFromJSON(query);

        int tempId = Integer.parseInt( ((Number)res.get("id")).toString());
        String tempTitle = (String)res.get("title");
        String tempStreet = (String)res.get("street");
        int tempNumbOfRoom = Integer.parseInt( ((Number)res.get("numberOfRoom")).toString());
        String tempFaculty = (String)res.get("name");
        String tempState = (String)res.get("state");

        Request req = new Request("INSERT INTO FACULTY (name)\n" +
                "SELECT * \n" +
                "FROM (SELECT '"+tempFaculty+"' as temp) as temt\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT name FROM FACULTY WHERE name = '"+tempFaculty+"'\n" +
                ")\n" +
                "\n" +
                "update HOSTEL\n" +
                "set title = '"+tempTitle+"', \n" +
                "\tstreet = '"+tempStreet+"', \n" +
                "\tNUMBER_OF_ROOM = "+tempNumbOfRoom+", \n" +
                "\tSTATE = '"+tempState+"',\n" +
                "\tFACULTY_ID = (Select FACULTY_ID from FACULTY where(FACULTY.NAME = '"+tempFaculty+"'))\n" +
                "where HOSTEL_ID = "+tempId+";"
        );
        String response = (String)req.exec();
        return response;
    }

    public static String deleteStudents(String query, ArrayList<Students> studentArr){
        try{
            Map<String,Object>res = Support.getFromJSON(query);

            final int tempId = (Integer) res.get("id");
            Request req = new Request("Delete from STUDENT where STUDENT_ID ="+tempId);

            for(int i =0; i<Facade.allStudents.size(); i++){
                if(tempId == Facade.allStudents.get(i).id){
                    Facade.allStudents.remove(Facade.allStudents.get(i));
                }
            }
            String response = (String)req.exec();
            return response;
        }
        catch (Exception ex){
            return ex.toString();
        }

    }

    public static ArrayList<Students> getStudentsByPages(String query){
        ArrayList<Students> result = new ArrayList<>();
        int tempId = Integer.parseInt(query);
        studentsBuild stdb = new studentsBuild();
        Data data = new Data(stdb);

        try{
            RequestThird req = new RequestThird("select STUDENT_ID, \"GROUP\".GROUP_KEY, \"GROUP\".GROUP_NUMB, FACULTY.NAME, HOSTEL_ID, PRIVILEGE, STATE, insert_data\n" +
                    "from STUDENT LEFT OUTER JOIN FACULTY\n" +
                    "ON STUDENT.FACULTY_ID = FACULTY.FACULTY_ID\n" +
                    "LEFT OUTER JOIN \"GROUP\"\n" +
                    "ON STUDENT.GROUP_ID = \"GROUP\".GROUP_ID\n" +
                    "where STUDENT_ID between "+((tempId-1)*5000)+" and "+(tempId*5000)+";");
            ResultSet res = (ResultSet) req.exec();
            data.changeBuilder(stdb);
            while (res.next()) {
                data.insideData.id = res.getInt("STUDENT_ID");
                data.insideData.letters = res.getString("GROUP_KEY");
                data.insideData.numbOfGroup = res.getInt("GROUP_NUMB");
                data.insideData.faculty = res.getString("NAME");
                data.insideData.hostel = Facade.InsertThirdData.getHostelById(res.getInt("HOSTEL_ID"), Facade.allHostelsThird);
                data.insideData.priv = res.getInt("PRIVILEGE");
                data.insideData.state = res.getString("STATE");
                data.insideData.date = res.getString("insert_data");
                data.insert("student");
                Students stdTemp = new Students();
                stdTemp = stdb.getResult();
                stdTemp.fromWhere = "third";
                result.add(stdTemp);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
