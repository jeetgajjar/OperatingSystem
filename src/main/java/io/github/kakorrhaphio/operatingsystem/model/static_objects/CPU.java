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
    public static void execute(PCB process) {
        process.current_cycle ++;
    }

    public static long getClock() {
        return System.currentTimeMillis() - time_zero;
    }

    public static int random(int min, int max){
        return (int)((max - min) * Math.random() + min);
    }


}