package com.mycompany.app.Hendler;

import com.mycompany.app.*;
import com.mycompany.app.Objects.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;

public class HandlerSecond {

    public static class StudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String tempPath = Support.parsGetRequest(exc);

            if("GET".equals(exc.getRequestMethod())){
                try{

                    String[] q = tempPath.split("\\?");
                    String tempState = "F";

                    for(int i =0; i< q.length;i++){
                        if(q[i].contains("query=")){
                            q[i] = q[i].replaceAll("query","");
                            q[i] = q[i].replaceAll("=","");
                            tempState = q[i];
                        }
                    }

                    Thread.sleep(25000);
                    ArrayList<Students> getStudents = CrudData.getStudentByState(tempState, Facade.allStudentsSecond);
                    Support.makeResponse(exc, getStudents);
                    DatabaseConectionSecond.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public static class AllStudentsHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");


            if ("GET".equals(exc.getRequestMethod())) {
                try {
                    Thread.sleep(5000);
                    ArrayList<Students> getStudents = Facade.allStudentsSecond;
                    Support.makeResponse(exc, getStudents);
                    DatabaseConectionSecond.closeConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static class UsersHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            if("GET".equals(exc.getRequestMethod())){
                try{
                    String tempPath = Support.parsGetRequest(exc);

                    String[] q = tempPath.split("&");
                    String tempName = "Hostel 1";

                    for(int i =0; i< q.length;i++){
                        if(q[i].contains("query=")){
                            q[i] = q[i].replaceAll("query","");
                            q[i] = q[i].replaceAll("=","");
                            q[i] = q[i].replaceAll("&","");
                            q[i] = q[i].replaceAll("%20"," ");

                            tempName = q[i];
                        }
                    }

                    Thread.sleep(25000);
                    ArrayList<User> getUsers = CrudData.getUserByHostelName(tempName, Facade.allUsersSecond);
                    Support.makeResponse(exc, getUsers);
                    DatabaseConectionSecond.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }

    public static class HostelsHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String tempPath = Support.parsGetRequest(exc);

            String[] q = tempPath.split("\\?");
            String tempFaculty = "RTF";

            for(int i =0; i< q.length;i++){
                if(q[i].contains("query=")){
                    q[i] = q[i].replaceAll("query","");
                    q[i] = q[i].replaceAll("=","");
                    tempFaculty = q[i];
                }
            }

            if("GET".equals(exc.getRequestMethod())){
                try{
                    Thread.sleep(25000);
                    ArrayList<Hostel> getUsers = CrudData.getHostelByFaculty(tempFaculty, Facade.allHostelsSecond);
                    Support.makeResponse(exc, getUsers);
                    DatabaseConectionSecond.closeConnection();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
}
