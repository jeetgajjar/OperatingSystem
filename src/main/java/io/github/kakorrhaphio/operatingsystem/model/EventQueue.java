package io.github.kakorrhaphio.operatingsystem.model;

/**
 * Created by class on 10/13/16.
 */
public class EventQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static EventQueue instance = new EventQueue();
    private EventQueue(){}
    public static EventQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO:
    enQueue()
    deQueue()
     */

}
