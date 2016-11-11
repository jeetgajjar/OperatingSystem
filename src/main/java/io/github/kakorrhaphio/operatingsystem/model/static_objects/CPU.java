package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

/**
 * Created by class on 10/13/16.
 */
public class CPU {
    public static final int NEW = 0;
    public static final int READY = 1;
    public static final int RUN = 2;
    public static final int WAIT = 3;
    public static final int EXIT = 4;

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


    //TODO: CPU.execute()
    public static boolean execute(PCB process, int cycles) {
        //boolean io = process.exec(cycles);
        //return io;
        return true;
    }

    public static long getClock() {
        return System.currentTimeMillis() - time_zero;
    }

    //extra, turns state int to string for display
    public static String toString(int state){
        switch (state){
            case NEW : return "NEW__";
            case READY : return "READY";
            case RUN : return "RUN__";
            case WAIT : return "WAIT_";
            case EXIT : return "EXIT_";
            default: return "err!!";
        }
    }
}