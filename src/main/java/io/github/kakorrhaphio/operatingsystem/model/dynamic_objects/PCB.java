package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import io.github.kakorrhaphio.operatingsystem.view.Log;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.concurrent.Callable;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */

//TODO: implement ageing <- scheduling algorithms
public class PCB {
    private static int pid_head = 0;

    public int priority;
    public int state;
    public int current_cycle;
    public int cycles;
    public int memory;
    public int io;
    public int kernal_bit;
    public int pid;
    public Callable<Integer> run;


    public PCB(int priority, int cycles, int memory, int io, int kernel_bit, Callable<Integer> run){
        this.pid = gen_pid(); // should outsource
        this.priority = priority;
        this.cycles = cycles;
        this.memory = memory;
        this.state = V.NEW;
        this.current_cycle = 0;
        this.io = io;
        this.kernal_bit = kernel_bit;
        this.run = run;
    }

    public void run(){
        if (this.run == null){
            return;
        }
        try{
            this.run.call();
        } catch (Exception e){
            Log.e("PCB", "Tried running process " + Integer.toString(this.pid) + ", threw exception");
        }
    }

    //TODO: this should be outsourced to dispatcher/threading manager/process manager
    private static int gen_pid(){
        pid_head ++;
        return pid_head;
    }

    //TODO: toString outsource to process manager
    public String toString(){
        String out = "[";
        out += Integer.toString(this.pid) + ";";
        out += V.pstate(this.state) + ";";
        out += Integer.toString(this.memory) + ";";
        out += V.kernelbit(this.kernal_bit) + "]";
        return out;
    }
}
