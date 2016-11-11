package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class Scheduler {
    public static final int MEMORY_LIMIT = 256000;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> dumb_schedule_ready;
    private static ArrayList<PCB> dumb_schedule_wait;
    private static long time_adjustment;
    private static int memory_usage_size;
    private static Scheduler instance = new Scheduler();
    private Scheduler() {
        dumb_schedule_ready = new ArrayList();
        time_adjustment = 0;
        memory_usage_size = 0;
    }
    public static Scheduler getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *



    //TODO: all scheduling
    public static boolean insertPCB(PCB to_insert){
        return true;
    }





    public static boolean removePCB(PCB to_remove){
        if(dumb_schedule_ready.contains(to_remove)){
            dumb_schedule_ready.remove(to_remove);
            return true;
        }
        return false;
    }

    public static int getState(PCB block){
        if(dumb_schedule_ready.contains(block)){
            return block.state;
        }
        return -1;
    }

    public static boolean setState(PCB block, int state){
        if(dumb_schedule_ready.contains(block)){
            block.state = state;
            return true;
        }
        return false;
    }

    public static int getWait(PCB block){
        if(dumb_schedule_ready.contains(block)){
            return block.wait;
        }
        return -1;
    }

    public static boolean setWait(PCB block, int wait){
        if(dumb_schedule_ready.contains(block)){
            block.wait = wait;
            return true;
        }
        return false;
    }

    public static int getMemory_usage_size(){
        return memory_usage_size;
    }


    /*TODO
    getArrival()

    setArrival()
     */


    public static long getCPUTime(){
        return time_adjustment + CPU.getClock();
    }

    public static void setCPUTime(long new_time_adjustment){
        time_adjustment = new_time_adjustment;
    }
}
