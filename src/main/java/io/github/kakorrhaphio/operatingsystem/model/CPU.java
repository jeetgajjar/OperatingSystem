package io.github.kakorrhaphio.operatingsystem.model;

/**
 * Created by class on 10/13/16.
 */
public class CPU {
    /*TODO
    execute()
    getClock()
     */
    public boolean execute(PCB process, int time){
        boolean io = process.exec(time);
        return io;
    }

    public long getClock(){
        return System.currentTimeMillis();
    }
}
