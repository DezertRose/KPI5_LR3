package com.mycompany.app;

import java.sql.*;


public class Request {
    private String request;

    public Request(String request){
        this.request = request;
    }

    public Object exec(){
        GetRequest get = new GetRequest();
        BasicRequest basic = new BasicRequest();
        InsertRequest insert = new InsertRequest();
        DeleteRequest delete = new DeleteRequest();
        UpdateRequest update = new UpdateRequest();

        basic.setNext(get);
        get.setNext(insert);
        insert.setNext(delete);
        delete.setNext(update);

        Object tempObj = basic.handler(get_type_request(request), request);
        return tempObj;
    }

    private String get_type_request (String tempRequest){

        String type = tempRequest.split(" ")[0].toUpperCase();

        return type;
    }

    public interface HandlerRequest {

        public void setNext(HandlerRequest h);
        public Object handler(String type, String request);
    }


    public class BasicRequest implements HandlerRequest{
        public HandlerRequest next;

        public void setNext(HandlerRequest h){
            next = h;
        };

        public Object handler(String type, String request){
            if (next != null){
                Object answer = next.handler(type, request);
                return answer;
            }
            else{
                return "Wrong request";
            }
        };
    }


    public class GetRequest extends BasicRequest{
        @Override
        public Object handler(String type, String request){

            if (type.equals("SELECT")){

                try {
                    ResultSet rest = null;

                    Connection connect = DatabaseConection.connectToDB();
                    Statement state = connect.createStatement();
                    rest = state.executeQuery(request);

                    return rest;
                }
                catch (Exception ex) {
                    ex.printStackTrace();

                    return "Error in select";
                }
            }
            else{

                Object ans = next.handler(type, request);

                return ans;
            }
        };
    }

    public class InsertRequest extends BasicRequest{

        @Override
        public Object handler(String type, String request) {
            if(type.equals("INSERT")){
                try{
                    Connection connect = DatabaseConection.connectToDB();
                    connect.prepareStatement(request).execute();
                    return "Insert statement";
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    return "Error in Insert";
                }
            }
            else{
                Object answer = next.handler(type, request);
                return answer;
            }
        }
    }

    public class DeleteRequest extends BasicRequest{
        @Override
        public Object handler(String type, String request) {
            if (type.equals("DELETE")) {
                try {
                    Connection connect = DatabaseConection.connectToDB();
                    connect.prepareStatement(request).execute();
                    return "Delete statement";
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return "Erorr in delete";
                }
            }
            else{
                Object answer = next.handler(type, request);
                return answer;
            }
        };
    }

    public class UpdateRequest extends BasicRequest{
        @Override
        public Object handler(String type, String request) {
            try {
                Connection connect = DatabaseConection.connectToDB();
                connect.prepareStatement(request).execute();
                return "Update statement";
            }
            catch (Exception ex){
                ex.printStackTrace();
                return "Error in update";
            }
        }
    }
}
