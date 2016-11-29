package sample;

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

    // Kernel bit
    public static final int USER = 0;
    public static final int KERNEL = 1;

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
        PCB new_pcb = new PCB (0,4,input);
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
        PCB new_pcb = new PCB (0,77,400);
        Quantum[] code = new Quantum[3];
        code[0] = new Quantum(6, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                System.out.println("one");
                return new String[]{"w","Running OUT operation - - - - - - -\n"};
            }
        },new_pcb);
        code[1] = new Quantum(20, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                System.out.println("two");
                return new String[]{"a",ExecutionQueue.printing()};
            }
        },new_pcb);
        code[2] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                System.out.println("three");
                return new String[]{"p"};
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // builds IO process
    public static PCB new_IO_process () {
        int rand_buf_size = CPU.random(50,200);
        int rand_time = CPU.random(30,50);
        PCB new_pcb = new PCB (0,3,rand_buf_size);
        Quantum[] code = new Quantum[1];
        code[0] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                return new String[] {"io",Integer.toString(rand_buf_size),Integer.toString(rand_time)};
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // builds YIELD process
    public static PCB new_YIELD_process () {
        PCB new_pcb = new PCB (0,3,7);
        Quantum[] code = new Quantum[1];
        code[0] = new Quantum(4, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                InterruptProcessor.signalInterrupt(null);
                return new String[0];
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // TODO: Make this a background process, underlying process shouldn't be "all at once"
    // de-fragment memory
    public static PCB new_DEFRAG_process () {
        PCB new_pcb = new PCB (1,10, 14);
        Quantum[] code = new Quantum[1];
        code[0] = new Quantum(5, new Callable<String[]>() {
            @Override
            public String[] call() throws Exception {
                MemoryManager.defrag();
                return new String[0];
            }
        },new_pcb);
        return new_pcb.set_code(code);
    }

    // standard print statement for all pcbs
    public static String toString(PCB p_in){
        String out = "[";
        out += Integer.toString(p_in.pid) + ";";
        out += pstate(p_in.state) + ";";
        out += Integer.toString(p_in.memory_size) + ";";
        out += kernelbit(p_in.kernel_bit) + "]";
        return out;
    }

    public static int gen_pid(){
        pid_head ++;
        return pid_head;
    }

    // Process state toString
    public static String pstate(int state){
        switch (state) {
            case NEW:
                return "NEW";
            case READY:
                return "READY";
            case RUN:
                return "RUN";
            case WAIT:
                return "WAIT";
            case EXIT:
                return "EXIT";
            default:
                //Log.e("V","Process state toString method called with non-state parameter");
                return "";
        }
    }

    public static void clean() {
        pid_head = 0;
    }

    // Kernel bit toString
    public static String kernelbit(int kernelbit){
        switch (kernelbit) {
            case USER:
                return "USER";
            case KERNEL:
                return "KERNEL";
            default:
                //Log.e("V","Kernel bit toString method called with non-kernel parameter");
                return "";
        }
    }
}
