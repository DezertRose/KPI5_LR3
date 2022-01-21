package com.mycompany.app;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.mycompany.app.Hendler.*;
import com.mycompany.app.Objects.*;
import com.mycompany.app.ToBuild.Data;
import com.mycompany.app.ToBuild.*;
import com.sun.net.httpserver.HttpServer;
import java.util.*;
import java.sql.*;

public class Facade {

    public static ArrayList<Hostel> allHostels;
    public static ArrayList<User> allUsers;
    public static ArrayList<Students> allStudents;

    public static ArrayList<Hostel> allHostelsSecond;
    public static ArrayList<User> allUsersSecond;
    public static ArrayList<Students> allStudentsSecond;

    public static ArrayList<Hostel> allHostelsThird;
    public static ArrayList<User> allUsersThird;
    public static ArrayList<Students> allStudentsThird;

    public static ArrayList<Students> firstKesh;
    public static ArrayList<Students> secondKesh;

    public static void setDBConnection() {
        DatabaseConection.connectToDB();
        DatabaseConectionSecond.connectToDB();
        DatabaseConectionThird.connectToDB();
    }

    public static void BeginService(String name, int port) throws IOException {
        try {
            InetSocketAddress address = new InetSocketAddress(name, port);
            HttpServer httpServer = HttpServer.create(address, 0);

            allHostels = HostelsData.setHostelData();
            allUsers = HostelsData.setUserData(allHostels);
            allStudents = HostelsData.setStudentsData(allHostels);

            httpServer.createContext("/student", new Hendlers.StudentsHandler());
            httpServer.createContext("/user", new Hendlers.UserHandler());
            httpServer.createContext("/hostels", new Hendlers.HostelsHandler());

            httpServer.setExecutor(null);
            httpServer.start();
            System.out.println("HTTP service on port 5000");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP service");
            exception.printStackTrace();
        }
    }

    public static void BeginSecondService(String name, int port) throws IOException {
        try {
            InetSocketAddress address = new InetSocketAddress(name, port);
            HttpServer httpServer = HttpServer.create(address, 0);

            allHostelsSecond = InsertSecondData.setHostelData();
            allUsersSecond = InsertSecondData.setUserData(allHostelsSecond);
            allStudentsSecond = InsertSecondData.setStudentsData(allHostelsSecond);

            httpServer.createContext("/hostel/search", new HandlerSecond.HostelsHandler());
            httpServer.createContext("/students/search", new HandlerSecond.StudentsHandler());
            httpServer.createContext("/students", new HandlerSecond.AllStudentsHandler());
            httpServer.createContext("/users/search", new HandlerSecond.UsersHandler());

            httpServer.setExecutor(null);
            httpServer.start();
            System.out.println("HTTP service on port 5001");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP service 5001");
            exception.printStackTrace();
        }
    }

    public static void BeginThirdService(String name, int port) throws IOException {
        try {
            InetSocketAddress address = new InetSocketAddress(name, port);
            HttpServer httpServer = HttpServer.create(address, 0);

            allHostelsThird = InsertThirdData.setHostelData();
            allUsersThird = InsertThirdData.setUserData(allHostelsThird);
            allStudentsThird = InsertThirdData.setStudentsData(allHostelsThird);

            httpServer.createContext("/price-list", new HendlerThird.StudentsHandler());
            httpServer.createContext("/details", new HendlerThird.StudentsIdHandler());

            httpServer.setExecutor(null);
            httpServer.start();
            System.out.println("HTTP service on port 5003");

        } catch (Exception exception) {
            System.out.println("Failed to create HTTP service 5003");
            exception.printStackTrace();
        }
    }

    public static class HostelsData{
        public static ArrayList<Hostel> setHostelData() {
            hostelBuild hostBuilder = new hostelBuild();

            Data data = new Data(hostBuilder);

            return getAllHostels(data, hostBuilder);
        }

        public static ArrayList<User> setUserData(ArrayList<Hostel> hostData) {
            userBuild usBuilder = new userBuild();

            Data data = new Data(usBuilder);

            return getAllUsers(data, usBuilder, hostData);
        }

        public static ArrayList<Students> setStudentsData(ArrayList<Hostel> hostData){
            studentsBuild stdBuilder = new studentsBuild();

            Data data = new Data(stdBuilder);

            return getAllstudents(data, stdBuilder, hostData);
        }

        public static ArrayList<Students> getAllstudents(Data data, studentsBuild stdb, ArrayList<Hostel> hostData){
            ArrayList<Students> students_arr = new ArrayList<>();

            try{
                Request req = new Request("select STUDENT_ID, \"GROUP\".GROUP_KEY, \"GROUP\".GROUP_NUMB, FACULTY.NAME, HOSTEL_ID, PRIVILEGE, STATE, insert_data\n" +
                        "from STUDENT LEFT OUTER JOIN FACULTY \n" +
                        "ON STUDENT.FACULTY_ID = FACULTY.FACULTY_ID \n" +
                        "LEFT OUTER JOIN \"GROUP\"\n" +
                        "ON STUDENT.GROUP_ID = \"GROUP\".GROUP_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(stdb);
                while (res.next()){
                    data.insideData.id = res.getInt("STUDENT_ID");
                    data.insideData.letters = res.getString("GROUP_KEY");
                    data.insideData.numbOfGroup = res.getInt("GROUP_NUMB");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.priv = res.getInt("PRIVILEGE");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.date = res.getString("insert_data");

                    data.insert("student");
                    students_arr.add(stdb.getResult());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return students_arr;
        }

        public static ArrayList<User> getAllUsers(Data data, userBuild ub, ArrayList<Hostel> hostData){
            ArrayList<User> users_arr = new ArrayList<>();

            try{
                Request req = new Request("select USER_ID, FACULTY.NAME, HOSTEL_ID, E_MAIL, PASSWORD\n" +
                        "from \"USER\" LEFT OUTER JOIN FACULTY \n" +
                        "ON \"USER\".FACULTY_ID = FACULTY.FACULTY_ID ");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(ub);

                while(res.next()){
                    data.insideData.id = res.getInt("USER_ID");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.mail = res.getString("E_MAIL");
                    data.insideData.password = res.getString("PASSWORD");
                    data.insert("user");
                    users_arr.add(ub.getResult());
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return users_arr;
        }

        public static Hostel getHostelById(int id, ArrayList<Hostel> host_arr){
            for (Hostel hostel : host_arr) {
                if (id == hostel.id) {
                    return hostel;
                }
            }
            return null;
        }

        public static ArrayList<Hostel> getAllHostels(Data data, hostelBuild hb){
            ArrayList<Hostel> host = new ArrayList<>();

            try{
                Request req = new Request("select HOSTEL_ID, TITLE, STREET, NUMBER_OF_ROOM, STATE, FACULTY.NAME as FACULTY_NAME from HOSTEL LEFT OUTER JOIN FACULTY ON HOSTEL.FACULTY_ID = FACULTY.FACULTY_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(hb);

                while (res.next()){
                    data.insideData.id = res.getInt("HOSTEL_ID");
                    data.insideData.name = res.getString("TITLE");
                    data.insideData.street = res.getString("STREET");
                    data.insideData.numbOfRooms = res.getInt("NUMBER_OF_ROOM");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.faculty = res.getString("FACULTY_NAME");
                    data.insert("hostel");
                    host.add(hb.getResult());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return host;
        };
    }

    public static class InsertSecondData{
        public static ArrayList<Hostel> setHostelData() {
            hostelBuild hostBuilder = new hostelBuild();

            Data data = new Data(hostBuilder);

            return getAllHostels(data, hostBuilder);
        }

        public static ArrayList<User> setUserData(ArrayList<Hostel> hostData) {
            userBuild usBuilder = new userBuild();

            Data data = new Data(usBuilder);

            return getAllUsers(data, usBuilder, hostData);
        }

        public static ArrayList<Students> setStudentsData(ArrayList<Hostel> hostData){
            studentsBuild stdBuilder = new studentsBuild();

            Data data = new Data(stdBuilder);

            return getAllstudents(data, stdBuilder, hostData);
        }

        public static ArrayList<Students> getAllstudents(Data data, studentsBuild stdb, ArrayList<Hostel> hostData){
            ArrayList<Students> students_arr = new ArrayList<>();

            try{
                RequestSecond req = new RequestSecond("select STUDENT_ID, \"GROUP\".GROUP_KEY, \"GROUP\".GROUP_NUMB, FACULTY.NAME, HOSTEL_ID, PRIVILEGE, STATE, insert_data\n" +
                        "from STUDENT LEFT OUTER JOIN FACULTY \n" +
                        "ON STUDENT.FACULTY_ID = FACULTY.FACULTY_ID \n" +
                        "LEFT OUTER JOIN \"GROUP\"\n" +
                        "ON STUDENT.GROUP_ID = \"GROUP\".GROUP_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(stdb);
                while (res.next()){
                    data.insideData.id = res.getInt("STUDENT_ID");
                    data.insideData.letters = res.getString("GROUP_KEY");
                    data.insideData.numbOfGroup = res.getInt("GROUP_NUMB");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.priv = res.getInt("PRIVILEGE");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.date = res.getString("insert_data");

                    data.insert("student");
                    students_arr.add(stdb.getResult());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return students_arr;
        }

        public static ArrayList<User> getAllUsers(Data data, userBuild ub, ArrayList<Hostel> hostData){
            ArrayList<User> users_arr = new ArrayList<>();

            try{
                RequestSecond req = new RequestSecond("select USER_ID, FACULTY.NAME, HOSTEL_ID, E_MAIL, PASSWORD\n" +
                        "from \"USER\" LEFT OUTER JOIN FACULTY \n" +
                        "ON \"USER\".FACULTY_ID = FACULTY.FACULTY_ID ");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(ub);

                while(res.next()){
                    data.insideData.id = res.getInt("USER_ID");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.mail = res.getString("E_MAIL");
                    data.insideData.password = res.getString("PASSWORD");
                    data.insert("user");
                    users_arr.add(ub.getResult());
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return users_arr;
        }

        public static Hostel getHostelById(int id, ArrayList<Hostel> host_arr){
            for (Hostel hostel : host_arr) {
                if (id == hostel.id) {
                    return hostel;
                }
            }
            return null;
        }

        public static ArrayList<Hostel> getAllHostels(Data data, hostelBuild hb){
            ArrayList<Hostel> host = new ArrayList<>();

            try{
                RequestSecond req = new RequestSecond("select HOSTEL_ID, TITLE, STREET, NUMBER_OF_ROOM, STATE, FACULTY.NAME as FACULTY_NAME from HOSTEL LEFT OUTER JOIN FACULTY ON HOSTEL.FACULTY_ID = FACULTY.FACULTY_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(hb);

                while (res.next()){
                    data.insideData.id = res.getInt("HOSTEL_ID");
                    data.insideData.name = res.getString("TITLE");
                    data.insideData.street = res.getString("STREET");
                    data.insideData.numbOfRooms = res.getInt("NUMBER_OF_ROOM");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.faculty = res.getString("FACULTY_NAME");
                    data.insert("hostel");
                    host.add(hb.getResult());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return host;
        };
    }

    public static class InsertThirdData{
        public static ArrayList<Hostel> setHostelData() {
            hostelBuild hostBuilder = new hostelBuild();

            Data data = new Data(hostBuilder);

            return getAllHostels(data, hostBuilder);
        }

        public static ArrayList<User> setUserData(ArrayList<Hostel> hostData) {
            userBuild usBuilder = new userBuild();

            Data data = new Data(usBuilder);

            return getAllUsers(data, usBuilder, hostData);
        }

        public static ArrayList<Students> setStudentsData(ArrayList<Hostel> hostData){
            studentsBuild stdBuilder = new studentsBuild();

            Data data = new Data(stdBuilder);

            return getAllstudents(data, stdBuilder, hostData);
        }

        public static ArrayList<Students> getAllstudents(Data data, studentsBuild stdb, ArrayList<Hostel> hostData){
            ArrayList<Students> students_arr = new ArrayList<>();

            try{
                RequestThird req = new RequestThird("select STUDENT_ID, \"GROUP\".GROUP_KEY, \"GROUP\".GROUP_NUMB, FACULTY.NAME, HOSTEL_ID, PRIVILEGE, STATE, insert_data\n" +
                        "from STUDENT LEFT OUTER JOIN FACULTY \n" +
                        "ON STUDENT.FACULTY_ID = FACULTY.FACULTY_ID \n" +
                        "LEFT OUTER JOIN \"GROUP\"\n" +
                        "ON STUDENT.GROUP_ID = \"GROUP\".GROUP_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(stdb);
                while (res.next()){
                    data.insideData.id = res.getInt("STUDENT_ID");
                    data.insideData.letters = res.getString("GROUP_KEY");
                    data.insideData.numbOfGroup = res.getInt("GROUP_NUMB");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.priv = res.getInt("PRIVILEGE");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.date = res.getString("insert_data");

                    data.insert("student");
                    students_arr.add(stdb.getResult());
                }

            }catch (Exception ex){
                ex.printStackTrace();
            }
            return students_arr;
        }

        public static ArrayList<User> getAllUsers(Data data, userBuild ub, ArrayList<Hostel> hostData){
            ArrayList<User> users_arr = new ArrayList<>();

            try{
                RequestThird req = new RequestThird("select USER_ID, FACULTY.NAME, HOSTEL_ID, E_MAIL, PASSWORD\n" +
                        "from \"USER\" LEFT OUTER JOIN FACULTY \n" +
                        "ON \"USER\".FACULTY_ID = FACULTY.FACULTY_ID ");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(ub);

                while(res.next()){
                    data.insideData.id = res.getInt("USER_ID");
                    data.insideData.faculty = res.getString("NAME");
                    data.insideData.hostel = getHostelById(res.getInt("HOSTEL_ID"), hostData);
                    data.insideData.mail = res.getString("E_MAIL");
                    data.insideData.password = res.getString("PASSWORD");
                    data.insert("user");
                    users_arr.add(ub.getResult());
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            return users_arr;
        }

        public static Hostel getHostelById(int id, ArrayList<Hostel> host_arr){
            for (Hostel hostel : host_arr) {
                if (id == hostel.id) {
                    return hostel;
                }
            }
            return null;
        }

        public static ArrayList<Hostel> getAllHostels(Data data, hostelBuild hb){
            ArrayList<Hostel> host = new ArrayList<>();

            try{
                RequestThird req = new RequestThird("select HOSTEL_ID, TITLE, STREET, NUMBER_OF_ROOM, STATE, FACULTY.NAME as FACULTY_NAME from HOSTEL LEFT OUTER JOIN FACULTY ON HOSTEL.FACULTY_ID = FACULTY.FACULTY_ID");
                ResultSet res = (ResultSet) req.exec();
                data.changeBuilder(hb);

                while (res.next()){
                    data.insideData.id = res.getInt("HOSTEL_ID");
                    data.insideData.name = res.getString("TITLE");
                    data.insideData.street = res.getString("STREET");
                    data.insideData.numbOfRooms = res.getInt("NUMBER_OF_ROOM");
                    data.insideData.state = res.getString("STATE");
                    data.insideData.faculty = res.getString("FACULTY_NAME");
                    data.insert("hostel");
                    host.add(hb.getResult());
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return host;
        };
    }
}
