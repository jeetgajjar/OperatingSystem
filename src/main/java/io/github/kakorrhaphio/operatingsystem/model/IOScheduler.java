package io.github.kakorrhaphio.operatingsystem.model;

/**
 * Created by class on 10/13/16.
 */
public class IOScheduler {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static IOScheduler instance = new IOScheduler();
    private IOScheduler(){}
    public static IOScheduler getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO:
    scheduleIO()
    startIO()
     */
}
