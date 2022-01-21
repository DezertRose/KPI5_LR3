package com.mycompany.app;
import java.sql.*;

public class DatabaseConection {
    static String db = "University_hostel";
    static String serverip="localhost";
    static String serverPort="1433";
    static String url = "jdbc:sqlserver://"+serverip+":"+serverPort+";databaseName="+db+"";
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    private DatabaseConection(){};
    private static Connection connect = null;

    public static Connection connectToDB() {
        String databaseUserName = "sa";
        String databasePassword = "1";
        try {
            if(connect == null){

                Class.forName(driver).getDeclaredConstructor().newInstance();
                connect = DriverManager.getConnection(url, databaseUserName, databasePassword);

                System.out.println("Conected to DB");
            }
            else {
                if (connect.isClosed()){

                    Class.forName(driver).getDeclaredConstructor().newInstance();
                    connect = DriverManager.getConnection(url, databaseUserName, databasePassword);

                    System.out.println("Conected to DB");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }


    public static void closeConnection(){
        try {
            
            connect.close();

            System.out.println("Close conection to DB");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}