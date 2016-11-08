package io.github.kakorrhaphio.operatingsystem.model;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class Scheduler {
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
    private static Scheduler instance = new Scheduler;
    private static ArrayList<PCB> dumb_schedual;
    private static long time_adjustment;
    //private static ArrayList<PCB> round_robin_2 = new ArrayList();

    private Scheduler {
        dumb_schedual = new ArrayList();
        time_adjustment = 0;
    }

    public static Scheduler getInstance(){
        return instance;
    }

    public static void insertPCB(PCB to_insert){
        dumb_schedual.add(to_insert);
    }

    public static void removePCB(PCB to_remove){
        dumb_schedual.remove(to_remove);
    }

    public static long getCPUTime(){
        return time_adjustment + CPU.getClock();
    }

    publiv static void setCPUTime(long new_time_adjustment){
        time_adjustment = new_time_adjustment;
    }
}
