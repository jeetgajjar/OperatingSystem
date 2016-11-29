package sample;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class EventQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<ECB> queue;
    private static EventQueue instance = new EventQueue();
    private EventQueue(){
        queue = new ArrayList<>();
    }
    public static EventQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(ECB to_add){
        queue.add(to_add);
    }

    public static ECB deQueue(){
        return queue.remove(0);
    }

    public static Object[] get_processes(){
        return queue.toArray();
    }

    public static String printing(){
        if (queue.isEmpty()){
            return "";
        }
        Object[] arr =  queue.toArray();
        String output = "";
        for (int i = 0; i < arr.length; i ++) {
            output += EventManager.toString((ECB)arr[i]) + "\n";
        }
        return output;
    }

    public static void clean(){
        queue = new ArrayList();
    }
}
