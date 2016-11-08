package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class Scheduler {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> dumb_schedual;
    private static long time_adjustment;
    private static Scheduler instance = new Scheduler();
    private Scheduler() {
        dumb_schedual = new ArrayList();
        time_adjustment = 0;
    }
    public static Scheduler getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    /*TODO
    insertPCB()

    removePCB()

    getState()

    setState()

    getWait()

    setWait()

    getArrival()

    setArrival()

    getCPUTime()

    setCPUTime()
     */



    public static void insertPCB(PCB to_insert){
        dumb_schedual.add(to_insert);
    }

    public static void removePCB(PCB to_remove){
        dumb_schedual.remove(to_remove);
    }

    public static long getCPUTime(){
        return time_adjustment + CPU.getClock();
    }

    public static void setCPUTime(long new_time_adjustment){
        time_adjustment = new_time_adjustment;
    }
}
