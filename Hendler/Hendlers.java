package com.mycompany.app.Hendler;

import com.mycompany.app.DatabaseConection;
import com.mycompany.app.Facade;
import com.mycompany.app.Objects.Hostel;
import com.mycompany.app.Objects.Students;
import com.mycompany.app.Objects.User;
import com.mycompany.app.ToBuild.Data;
import com.mycompany.app.ToBuild.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Hendlers {

    public static class StudentsHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String query = Support.toParseRequest(exc);

            if("GET".equals(exc.getRequestMethod())){
                try{
                    ArrayList<Students> allData = new ArrayList<>(Facade.allStudents);

                    if(Facade.firstKesh == null){
                        Facade.firstKesh = new ArrayList<>();
                        try{
                            URL url = new URL("http://localhost:5001/students");

                            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
                            connect.setRequestMethod("GET");

                            StringBuilder data = new StringBuilder();
                            try(BufferedReader input = new BufferedReader(new InputStreamReader(connect.getInputStream()))){
                                String tempLine;
                                while((tempLine = input.readLine()) != null){
                                    data.append(tempLine);
                                }
                            }
                            ArrayList<Object> strArr = Support.getArrFromJson(data.toString());
                            ArrayList<Map<String, Object>> mso = new ArrayList<>();
                            strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                            for (Map<String, Object> s : mso) {
                                hostelBuild hstB = new hostelBuild();
                                studentsBuild stdB = new studentsBuild();
                                Data director = new Data(hstB);
                                Hostel host = new Hostel();

                                Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                                if (hostMap != null){
                                    director.insideData.id = (int)hostMap.get("id");
                                    director.insideData.name = (String)hostMap.get("name");
                                    director.insideData.street = (String)hostMap.get("street");
                                    director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                                    director.insideData.state = (String)hostMap.get("state");
                                    director.insideData.faculty = (String)hostMap.get("faculty");
                                    director.insert("hostel");
                                    host = hstB.getResult();
                                }
                                director.changeBuilder(stdB);

                                director.insideData.id = (int) s.get("id");
                                director.insideData.letters = (String) s.get("letters");
                                director.insideData.numbOfGroup = (int)s.get("numb_of_group");
                                director.insideData.faculty =(String) s.get("faculty");
                                director.insideData.priv = (int)s.get("privilege");
                                director.insideData.state = (String) s.get("state");
                                director.insideData.date = (String) s.get("date");
                                director.insideData.hostel = host;

                                Students student = new Students();
                                director.insert("");
                                student = stdB.getResult();
                                student.fromWhere = "second";
                                Facade.firstKesh.add(student);
                            }
                        }
                        catch (Exception ex){ex.printStackTrace();}
                    }
                    if (Facade.firstKesh != null){
                        allData.addAll(Facade.firstKesh);
                    }

                    if (Facade.secondKesh == null){
                        Facade.secondKesh = new ArrayList<>();

                        try{
                            for (int i=1; i<11; i++){
                                URL url = new URL("http://localhost:5003/price-list?p="+i);

                                HttpURLConnection conect = (HttpURLConnection)url.openConnection();
                                conect.setRequestMethod("GET");

                                StringBuilder strBuild = new StringBuilder();
                                try(BufferedReader in = new BufferedReader(new InputStreamReader(conect.getInputStream()))) {
                                    String line;
                                    while ((line = in.readLine()) != null){
                                        strBuild.append(line);
                                    }
                                }

                                ArrayList<Object> strArr = Support.getArrFromJson(strBuild.toString());
                                ArrayList<Map<String, Object>> mso = new ArrayList<>();
                                strArr.forEach(el -> {mso.add((Map<String, Object>) el);});

                                for (Map<String, Object> s : mso) {
                                    hostelBuild hstB = new hostelBuild();
                                    studentsBuild stdB = new studentsBuild();
                                    Data director = new Data(hstB);
                                    Hostel host = new Hostel();

                                    Map<String, Object> hostMap = (Map<String, Object>) s.get("hostel");
                                    if (hostMap != null){
                                        director.insideData.id = (int)hostMap.get("id");
                                        director.insideData.name = (String)hostMap.get("name");
                                        director.insideData.street = (String)hostMap.get("street");
                                        director.insideData.numbOfRooms = (int)hostMap.get("numb_of_room");
                                        director.insideData.state = (String)hostMap.get("state");
                                        director.insideData.faculty = (String)hostMap.get("faculty");
                                        director.insert("hostel");
                                        host = hstB.getResult();
                                    }
                                    director.changeBuilder(stdB);

                                    director.insideData.id = (int) s.get("id");
                                    director.insideData.letters = (String) s.get("letters");
                                    director.insideData.numbOfGroup = (int)s.get("numb_of_group");
                                    director.insideData.faculty =(String) s.get("faculty");
                                    director.insideData.priv = (int)s.get("privilege");
                                    director.insideData.state = (String) s.get("state");
                                    director.insideData.date = (String) s.get("date");
                                    director.insideData.hostel = host;

                                    Students student = new Students();
                                    director.insert("");
                                    student = stdB.getResult();
                                    student.fromWhere = "third";
                                    Facade.secondKesh.add(student);

                                }
                            }
                        }catch (Exception ex){ex.printStackTrace();}
                    }

                    if (Facade.secondKesh != null){
                        allData.addAll(Facade.secondKesh);
                    }

                    Support.makeResponse(exc, allData);
                    DatabaseConection.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else{
                if("DELETE".equals(exc.getRequestMethod())){
                    try{
                        String deleteData = CrudData.deleteStudents(query, Facade.allStudents);
                        Support.makeResponse(exc, deleteData);
                        DatabaseConection.closeConnection();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{Support.makeResponse(exc, "Unexpected problems");}
            }
        }
    }

    public static class UserHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String query = Support.toParseRequest(exc);

            if("GET".equals(exc.getRequestMethod())){
                try{
                    ArrayList<User> getUsers = Facade.allUsers;
                    Support.makeResponse(exc, getUsers);
                    DatabaseConection.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else{
                if ("POST".equals(exc.getRequestMethod())){
                    try {
                        String createData = CrudData.createNewUser(query, Facade.allUsers, Facade.allHostels);
                        Support.makeResponse(exc, createData);
                        DatabaseConection.closeConnection();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{Support.makeResponse(exc, "Unexpected problems");}
            }
        }
    }

    public static class HostelsHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers","Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE, PATCH");

            String query = Support.toParseRequest(exc);

            if("GET".equals(exc.getRequestMethod())){
                try{
                    ArrayList<Hostel> getHostel = Facade.allHostels;
                    Support.makeResponse(exc, getHostel);
                    DatabaseConection.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else{
                if ("PATCH".equals(exc.getRequestMethod())){
                    try{
                        String updateData = CrudData.updateHostel(query, Facade.allHostels);
                        Support.makeResponse(exc, updateData);
                        DatabaseConection.closeConnection();
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{Support.makeResponse(exc, "Unexpected problems");}
            }
        }
    }

}
