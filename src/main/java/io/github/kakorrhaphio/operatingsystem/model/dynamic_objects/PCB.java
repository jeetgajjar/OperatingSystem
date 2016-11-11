package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */
public class PCB {
    public int priority;
    public int state;
    public int current_cycle;
    public int wait;
    public int cycles;
    public int memory;
    public PCB(int priority_in){
        this.priority = priority_in;
    }
}
