package sample;

/**
 * Created by class on 10/13/16.
 */
public final class CPU {

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static long startTime;
    private static CPU instance = new CPU();
    private CPU() {
        startTime = System.currentTimeMillis();
    }
    public static CPU getInstance () {
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // gets called by scheduler
    public static int execute(final PCB process) {
        return process.run();
    }

    // will return time, used for time stamp
    public static long getClock() {
        return System.currentTimeMillis();
    }

    // will return number of milliseconds since CPU is instantiated
    public static long getTotalRunningTime () {
        return System.currentTimeMillis() - startTime;
    }

    // returns x,  min <= x < max
    public static int random (int min, int max) {
        return (int) ((max - min) * Math.random() + min);
    }

    public static void clean () {
        startTime = System.currentTimeMillis();
    }

}
