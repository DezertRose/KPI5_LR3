package com.mycompany.app.Hendler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Support {

    public static String toParseRequest(HttpExchange ex) throws IOException{
        InputStreamReader inStream = new InputStreamReader(ex.getRequestBody(), "utf-8");
        BufferedReader bufeReader = new BufferedReader(inStream);

        StringBuilder strBuild = new StringBuilder();
        int temp;

        while((temp = bufeReader.read()) != -1){
            strBuild.append((char)temp);
        }
        bufeReader.close();
        inStream.close();
        return strBuild.toString();
    };

    public static void makeResponse(HttpExchange ex, Object data) throws IOException{
        String res = makeJson(data);

        ex.sendResponseHeaders(200, res.getBytes(StandardCharsets.UTF_8).length);

        OutputStream outStream  = ex.getResponseBody();
        outStream.write(res.getBytes(StandardCharsets.UTF_8));
        outStream.close();
    }

    public static String makeJson(Object data){
        ObjectMapper obMap = new ObjectMapper();
        String ans = "";

        try{
            return obMap.writeValueAsString(data);
        }catch(Exception e){
            e.printStackTrace();
        }

        return ans;
    };

    public static String parsGetRequest(HttpExchange ex){
        try{
            return ex.getRequestURI().toString().split("\\?")[1].replaceAll("\\?", "");
        }
        catch (Exception e){
            return e.toString();
        }
    };

    public static ArrayList<Object> getArrFromJson(String temp){
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Object> map = new ArrayList<>();

        try{
            map = mapper.readValue(temp, new TypeReference<ArrayList<Object>>() {});
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    };

    public static Map<String,Object> getFromJSON(String data){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = new HashMap<>();
        try {
            map = mapper.readValue(data, new TypeReference<Map<String, Object>>(){});
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }
}

