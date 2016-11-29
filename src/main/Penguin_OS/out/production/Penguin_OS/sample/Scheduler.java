package sample;


/**
 * Created by class on 10/13/16.
 */
public class Scheduler {

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static long time_adjustment;
    private static Scheduler instance = new Scheduler();
    private Scheduler() {
        time_adjustment = 0;
    }
    public static Scheduler getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void run (int x) {
        int temp;
        int allocation;
        int cycles_count = 0;
        int internal_cycles_count;
        PCB current_process = ExecutionQueue.deQueue();
        while (cycles_count < x && current_process != null) {
            ECB interrupt = InterruptProcessor.are_there_any_interrupts();
            if (interrupt != null) cycles_count += interrupt.run();
            allocation = ExecutionQueue.cycleAllocation(current_process.getCycles_left());
            internal_cycles_count = 0;
            while (current_process.can_run(allocation)) {
                temp = current_process.run();
                internal_cycles_count += temp;
                allocation -= temp;
            }
            cycles_count += internal_cycles_count;
            ExecutionQueue.enQueue(current_process);
            current_process = ExecutionQueue.deQueue();
        }
    }

    public static void insertPCB(PCB to_insert){
        if (MemoryManager.add_process(to_insert)) {
            ExecutionQueue.enQueue(to_insert);
        } else {
            WaitQueue.enQueue(to_insert);
        }
    }

    public static PCB removePCB(){
        return ExecutionQueue.deQueue();
    }


    /*TODO Ask professor what these do
    getArrival()

    setArrival()

    getWait()

    setWait()

    getState()

    setState()
     */


    public static long getCPUTime(){
        return time_adjustment + CPU.getClock();
    }

    public static void setCPUTime(long new_time_adjustment){
        time_adjustment = new_time_adjustment;
    }


    public static void clean(){
        time_adjustment = 0;
    }

}
