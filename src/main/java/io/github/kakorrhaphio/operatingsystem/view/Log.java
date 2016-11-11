package io.github.kakorrhaphio.operatingsystem.view;

/**
 * Created by JuliusCeasar on 11/10/2016.
 */
public class Log {
    //TODO: add time stamps
    public static void i(String tag, String message){
        System.out.println("Logging Info ::: " + tag + " : " + message);
    }
    public static void e(String tag, String message){
        System.out.println("ERROR        /*************************");
        System.out.println("MESSAGE     |*** " + tag + " : " + message);
        System.out.println("!!!!!        \\*************************");
    }
}
