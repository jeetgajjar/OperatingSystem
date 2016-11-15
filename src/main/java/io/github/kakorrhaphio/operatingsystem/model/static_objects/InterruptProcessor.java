package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;

/**
 * Created by class on 10/13/16.
 */
public class InterruptProcessor {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static boolean interrupt;
    private static int id_head;
    private static InterruptProcessor instance = new InterruptProcessor();
    private InterruptProcessor(){
        interrupt = false;
        id_head = 0;
    }
    public static InterruptProcessor getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void signalInterrupt(int buffer_size){
        interrupt = true;
    }

    public static boolean hasInterrupt(){
        return interrupt;
    }

    public static void addEvent(ECB event_in){
        EventQueue.enQueue(event_in);
    }

    public static ECB getEvent(){
        return EventQueue.deQueue();
    }

    // generate new eid
    public static int gen(){
        id_head ++;
        return id_head;
    }

    public static void clean(){
        interrupt = false;
        id_head = 0;
    }
}
