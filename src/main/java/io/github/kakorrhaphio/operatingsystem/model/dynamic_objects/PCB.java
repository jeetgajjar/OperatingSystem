package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */

//TODO: implement ageing <- scheduling algorithms
public class PCB {
    public static final int NEW = 0;
    public static final int READY = 1;
    public static final int RUN = 2;
    public static final int WAIT = 3;
    public static final int EXIT = 4;

    public int priority;
    public int state;
    public int current_cycle;
    public int cycles;
    public int memory;
    public int needsIO;
    public int io_time;

    /**
     *
     * @param priority_in
     * @param cycles
     * @param memory
     * @param io_time = the length of time that the io will need to burst,
     * @param needs_io = clock cycle within this process that will require io if it requires io, otherwise -1
     */
    public PCB(int priority_in, int cycles, int memory, int io_time, int needs_io){
        this.priority = priority_in;
        this.cycles = cycles;
        this.memory = memory;
        this.state = NEW;
        this.current_cycle = 0;
        this.io_time = io_time;
        this.needsIO = needs_io;
    }

    //TODO: add rest of attributes
    public static String toString(PCB process){
        switch (state){
            case PCB.NEW : return "NEW__";
            case PCB.READY : return "READY";
            case PCB.RUN : return "RUN__";
            case PCB.WAIT : return "WAIT_";
            case PCB.EXIT : return "EXIT_";
            default: return "err!!";
        }
    }
}
