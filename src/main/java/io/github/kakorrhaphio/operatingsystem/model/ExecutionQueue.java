package io.github.kakorrhaphio.operatingsystem.model;

/**
 * Created by class on 10/13/16.
 */
public class ExecutionQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ExecutionQueue instance = new ExecutionQueue();
    private ExecutionQueue(){}
    public static ExecutionQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO:
    enQueue()
    deQueue()
     */
}
