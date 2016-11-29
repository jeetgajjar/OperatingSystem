package sample;


/**
 * Created by JuliusCeasar on 11/7/2016.
 */

//TODO: implement ageing <- scheduling algorithms
public class PCB {

    public final int pid;
    public final int kernel_bit;
    public final int priority;

    public int state;
    private int cycles_left;

    public int memory_head;
    public final int memory_size;

    private Quantum[] executable_code;
    private int quantum_counter;


    public PCB(int kernel_bit_in, int priority_in, int memory_size_in){
        pid = ProcessManager.gen_pid();
        kernel_bit = kernel_bit_in;
        priority = priority_in;
        state = ProcessManager.NEW;
        cycles_left = 0;
        memory_head = -1;
        memory_size = memory_size_in;
    }

    public int run () {
        if (quantum_counter == executable_code.length) {
            state = ProcessManager.EXIT;
            return 0;
        } else {
            executable_code[quantum_counter].run(memory_head);
            int cycles_executed = executable_code[quantum_counter].size();
            cycles_left -= cycles_executed;
            quantum_counter ++;
            if (quantum_counter == executable_code.length) state = ProcessManager.EXIT;
            return cycles_executed;
        }
    }

    public boolean can_run(int x) {
        if (quantum_counter >= executable_code.length) {
            return false;
        }
        if (executable_code[quantum_counter].size() <= x) {
            return true;
        }
        return false;
    }

    public int getCycles_left () {
        return cycles_left;
    }

    public PCB set_code (Quantum[] code_in) {
        if (executable_code == null) {
            executable_code = code_in;
            for (int i = 0; i < executable_code.length; i++) {
                cycles_left += executable_code[i].size();
            }
        }
        return this;
    }
}
