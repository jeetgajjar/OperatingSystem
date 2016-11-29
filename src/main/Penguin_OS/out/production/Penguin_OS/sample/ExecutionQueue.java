package sample;


import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class ExecutionQueue {

    // example robin algorithm values
    private static final int FIRST_ROBIN_SIZE_LIMIT = 20;
    private static final int FIRST_ROBIN_AGE_LIMIT = 100;

    private static final int SECOND_ROBIN_SIZE_LIMIT = 8;
    private static final int SECOND_ROBIN_AGE_LIMIT = 200;

    //TODO: include seperate kernal queue


    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> robin_user = new ArrayList<>(); //three stages, short rr, long rr, fifo
    private static int robin_age = 0;
    private static ExecutionQueue instance = new ExecutionQueue();
    private ExecutionQueue(){}
    public static ExecutionQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void build() {
        PCB cur = WaitQueue.deQueue();
        while (cur != null) {
            enQueue(cur);
            cur = WaitQueue.deQueue();
        }
    }

    public static void enQueue(PCB to_add){
        if (to_add.state == ProcessManager.WAIT || to_add.state == ProcessManager.NEW) {
            to_add.state = ProcessManager.READY;
            robin_user.add(to_add);
        } else if (to_add.state == ProcessManager.EXIT) {
            MemoryManager.remove_process(to_add.memory_head);
        }
    }

    public static PCB deQueue(){
        if (isEmpty()){
            build();
            if (isEmpty()) {
                return null;
            }
        }
        PCB temp = robin_user.remove(0);
        temp.state = ProcessManager.RUN;
        return temp;
    }

    public static Object[] get_processes(){
        return robin_user.toArray();
    }

    // this makes the execution algorithm two stage...
    // first stage is round robin with 10 cycles per process
    // second stage is "fifo", complete process till finish
    public static int cycleAllocation(int current_process_cycles) {
        if (robin_user.size() < SECOND_ROBIN_SIZE_LIMIT || robin_age > SECOND_ROBIN_AGE_LIMIT) {
            return current_process_cycles;
        } else if (robin_user.size() < FIRST_ROBIN_SIZE_LIMIT || robin_age > FIRST_ROBIN_AGE_LIMIT){
            return 20;
        } else{
            return 10;
        }
    }

    public static boolean isEmpty () {
        if (robin_user.isEmpty()) {
            return true;
        }
        return false;
    }

    public static String printing(){
        if (robin_user == null){
            return "";
        }
        Object[] arr =  robin_user.toArray();
        String output = "";
        for (int i = 0; i < arr.length; i ++) {
            output += ProcessManager.toString((PCB)arr[i]) + "\n";
        }
        return output;
    }

    public static void clean(){
        robin_user.clear();
    }
}
