package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;

/**
 * Created by class on 10/13/16.
 */
public class InterruptProcessor {
    //TODO: add interupt/signal values
    public static final int SIGKILL = 1;
    public static final int IO_INTERRUPT = 2;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static InterruptProcessor instance = new InterruptProcessor();
    private InterruptProcessor(){}
    public static InterruptProcessor getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO
    signalInterrupt()
    addEvent()
    getEvent()
     */

    public static void signalInterrupt(int signal){
        addEvent( new ECB(signal));
    }

    public static void addEvent(ECB event_in){
        EventQueue.enQueue(event_in);
    }

    public static ECB getEvent(){
        return EventQueue.deQueue();
    }
}
