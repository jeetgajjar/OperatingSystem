package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;


/**
 * Created by class on 10/13/16.
 */
public class CPU {

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static long time_zero;
    private static CPU instance = new CPU();
    private CPU() {
        time_zero = System.currentTimeMillis();
    }
    public static CPU getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // gets called by penguin
    public static void execute(PCB process) {
        process.cycles_left --;
    }

    // will return time, used for time stamp
    public static long getClock() {
        return System.currentTimeMillis();
    }

    // will return number of milliseconds since CPU is instantiated
    public static long getTotalRunningTime(){
        return System.currentTimeMillis() - time_zero;
    }

    // returns x,  min <= x < max
    public static int random(int min, int max){
        return (int)((max - min) * Math.random() + min);
    }


}