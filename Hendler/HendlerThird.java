package com.mycompany.app.Hendler;

import com.mycompany.app.DatabaseConectionThird;
import com.mycompany.app.Facade;
import com.mycompany.app.Objects.Students;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.ArrayList;

public class HendlerThird{

    public static class StudentsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String requestId = null;

            requestId = exc.getRequestURI().toString().split("\\?p=")[1];

            if ("GET".equals(exc.getRequestMethod())) {
                try {
                    ArrayList<Students> getStudents = CrudData.getStudentsByPages(requestId);
                    Support.makeResponse(exc, getStudents);
                    DatabaseConectionThird.closeConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static class StudentsIdHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exc) throws IOException {
            exc.getResponseHeaders().add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
            exc.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");

            String requestId = null;

            requestId = exc.getRequestURI().toString().split("/")[2];

            if ("GET".equals(exc.getRequestMethod())) {
                try {
                    ArrayList<Students> getStudents = CrudData.getStudentById(requestId, Facade.allStudentsThird);
                    Support.makeResponse(exc, getStudents);
                    DatabaseConectionThird.closeConnection();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
