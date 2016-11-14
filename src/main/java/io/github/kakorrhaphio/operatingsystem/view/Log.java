package io.github.kakorrhaphio.operatingsystem.view;

import java.util.Date;

/**
 * Created by JuliusCeasar on 11/10/2016.
 */
public class Log {
    private static Log instance = null;
    private static boolean logging = false;
    private static Date date;
    private Log(){}
    public static Log getInstance(){
        if (instance == null){
            instance = new Log();
            date = new Date();
        }
        return instance;
    }

    public static void setLogging(){
        logging = true;
    }

    public static void i(String tag, String message){
        if (logging) {
            System.out.println(date.toString() + " :: Info :: " + tag + " : " + message);
        }
    }
    public static void e(String tag, String message){
        if (logging) {
            System.out.println(date.toString() + " ::ERROR!:: " + tag + " : " + message);
        }
    }
}
