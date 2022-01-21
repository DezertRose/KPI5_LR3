package com.mycompany.app;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) throws Exception{
        Facade.setDBConnection();
        Facade.BeginService("localhost", 5000);

        Facade.BeginSecondService("localhost", 5001);

        Facade.BeginThirdService("localhost", 5003);

        //System.out.println(Facade.allUsersSecond);


    }
}
