package io.github.kakorrhaphio.operatingsystem.model;

/**
 * Created by class on 10/13/16.
 */
public class InterruptProcessor {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static InterruptProcessor instance = new InterruptProcessor();
    private InterruptProcessor(){}
    public static InterruptProcessor getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO
    signalInterupt()
    addEvent()
    getEvent()
     */
}
