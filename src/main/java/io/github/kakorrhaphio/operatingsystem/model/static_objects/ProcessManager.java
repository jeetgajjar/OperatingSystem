package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.Quantum;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by JuliusCeasar on 11/18/2016.
 */
public class ProcessManager {
    public static final int NEW = 0;
    public static final int READY = 1;
    public static final int RUN = 2;
    public static final int WAIT = 3;
    public static final int EXIT = 4;

    public static int pid_head = 0;
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ProcessManager instance = new ProcessManager();
    private ProcessManager() {}
    public static ProcessManager getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // PCB
    // int kernel_bit_in, int priority_in, int memory_size_in, Quantum[] executable_code_in

    // builds CALCULATE PCB
    public static PCB new_CALCULATE_process(int input){
        PCB new_pcb = new PCB (0,4,input, null);
        Quantum[] code = new Quantum[input / 5];
        for (int i = 0; i < input / 5; i ++) {
            if (i == input / 5 - 1) {
                code[i] = new Quantum(5 + input % 5,null,new_pcb);
            }
            code[i] = new Quantum(5,null, new_pcb);
        }
        return new_pcb.set_code(code);
    }

    // builds OUT PCB
    public static PCB new_OUT_process(){
        PCB new_pcb = new PCB (0,7,400,null);
        Quantum[] code = new Quantum[3];
        code[0] = new Quantum(6, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[]{"w","Running OUT operation - - - - - - -"};
            }
        },new_pcb);
        code[1] = new Quantum(20, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[]{"a",ExecutionQueue.printing()};
            }
        },new_pcb);
        code[2] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[]{"p"};
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // builds IO process
    public static PCB new_IO_process () {
        int rand_buf_size = CPU.random(50,200);
        int rand_time = CPU.random(30,50);
        PCB new_pcb = new PCB (0,3,rand_buf_size,null);
        Quantum[] code = new Quantum[2];
        code[0] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[] {"io",Integer.toString(rand_buf_size),Integer.toString(rand_time)};
            }
        },new_pcb);
        code[1] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[] {"p"};
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // standard print statement for all pcbs
    public static String toString(PCB p_in){
        String out = "[";
        out += Integer.toString(p_in.pid) + ";";
        out += V.pstate(p_in.state) + ";";
        out += Integer.toString(p_in.memory_size) + ";";
        out += V.kernelbit(p_in.kernel_bit) + "]";
        return out;
    }

    public static int gen_pid(){
        pid_head ++;
        return pid_head;
    }
}
