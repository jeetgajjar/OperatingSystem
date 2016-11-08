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


    //TODO: CPU.execute()
    public static boolean execute(PCB process, int time) {
        //boolean io = process.exec(time);
        //return io;
        return true;
    }

    public static long getClock() {
        return System.currentTimeMillis() - time_zero;
    }
}
