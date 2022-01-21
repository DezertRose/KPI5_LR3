package com.mycompany.app;

import java.sql.*;

public class RequestSecond {

    private String request;

    public RequestSecond(String request){
        this.request = request;
    }

    public Object exec(){
        GetRequest get = new GetRequest();
        BasicRequest basic = new BasicRequest();

        basic.setNext(get);

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


    public class BasicRequest implements HandlerRequest {
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


    public class GetRequest extends BasicRequest {
        @Override
        public Object handler(String type, String request){

            if (type.equals("SELECT")){

                try {
                    ResultSet rest = null;

                    Connection connect = DatabaseConectionSecond.connectToDB();
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
}
