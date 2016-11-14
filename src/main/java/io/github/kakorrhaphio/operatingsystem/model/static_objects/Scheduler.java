package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class Scheduler {

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> dumb_schedule_ready;
    private static ArrayList<PCB> dumb_schedule_wait;
    private static long time_adjustment;
    private static int memory_usage_size;
    private static Scheduler instance = new Scheduler();
    private Scheduler() {
        dumb_schedule_ready = new ArrayList();
        time_adjustment = 0;
        memory_usage_size = 0;
    }
    public static Scheduler getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static boolean insertPCB(PCB to_insert){
        // TODO: insert into CPU or into ready/wait queue?
    }



    public static boolean removePCB(PCB to_remove){
        // TODO: remove from CPU or from ready/wait queue?
    }

    public static int getState(PCB block){
        if(dumb_schedule_ready.contains(block)){
            return block.state;
        }
        return -1;
    }

    public static boolean setState(PCB block, int state){
        if(dumb_schedule_ready.contains(block)){
            block.state = state;
            return true;
        }
        return false;
    }

    public static int getMemory_usage_size(){
        return memory_usage_size;
    }


    /*TODO
    getArrival()

    setArrival()

    getWait()

    setWait()
     */


    public static long getCPUTime(){
        return time_adjustment + CPU.getClock();
    }

    public static void setCPUTime(long new_time_adjustment){
        time_adjustment = new_time_adjustment;
    }

    //TODO: log time that a particular process runs, ie get cpu time before and after each execution
    private static void runForNumberOfCycles(int number_of_cycles){
        int count = 0;
        while(!(count >= number_of_cycles || (ReadyQueue.isEmpty() && WaitQueue.isEmpty()))){
            // call scheduler to build execution queue from ready queue
            // also look at wait queue

            buildReadyFromWaitQueue();
            buildExecutionFromReadyQueue();

            //TODO: put "build execution queue" into a pcb with kernal bit


            // iterate through round robin till empty
            PCB current_process = ExecutionQueue.deQueue();
            while(current_process != null){
                current_process.state = V.RUN;
                int robin_allocated_time = ExecutionQueue.cycleAllocation(current_process.cycles_left);
                for(int i = 0; i < robin_allocated_time; i++){
                    if(InterruptProcessor.hasInterrupt()){
                        current_process.state = V.READY;
                        ExecutionQueue.enQueue(current_process);
                        count += InterruptProcessor.getEvent().run();
                        break;
                    }
                    if(count >= number_of_cycles){
                        current_process.state = V.READY;
                        ExecutionQueue.enQueue(current_process);
                        break;
                    }
                    if(current_process.cycles_left == 0){
                        current_process.state = V.EXIT;
                        break;
                    }
                    if(current_process.io == current_process.cycles_left){
                        current_process.state = V.WAIT;
                        IOScheduler.scheduleIO(current_process);
                        break;
                    }
                    count ++;
                    CPU.execute(current_process);
                }
                if(current_process.state == V.RUN){
                    current_process.state = V.READY;
                    ExecutionQueue.enQueue(current_process);
                }
                current_process = ExecutionQueue.deQueue();
            }
        }
    }

    //TODO: log time that a particular process runs, ie get cpu time before and after each execution
    private static void runTillEnd(){
        while(!(ReadyQueue.isEmpty() && WaitQueue.isEmpty())){
            // call scheduler to build execution queue from ready queue
            // also look at wait queue

            buildReadyFromWaitQueue();
            buildExecutionFromReadyQueue();

            //TODO: put "build execution queue" into a pcb with kernal bit

            // iterate through round robin till empty
            PCB current_process = ExecutionQueue.deQueue();
            while(current_process != null){
                current_process.state = V.RUN;
                int robin_allocated_time = ExecutionQueue.cycleAllocation(current_process.cycles_left);
                for(int i = 0; i < robin_allocated_time; i++){
                    if(InterruptProcessor.hasInterrupt()){
                        current_process.state = V.READY;
                        ExecutionQueue.enQueue(current_process);
                        break;
                    }
                    if(current_process.cycles_left == 0){
                        current_process.state = V.EXIT;
                        break;
                    }
                    if(current_process.io == current_process.cycles_left){
                        current_process.state = V.WAIT;
                        IOScheduler.scheduleIO(current_process);
                        break;
                    }
                    CPU.execute(current_process);
                }
                if(current_process.state == V.RUN){
                    current_process.state = V.READY;
                    ExecutionQueue.enQueue(current_process);
                }
                current_process = ExecutionQueue.deQueue();
            }
        }
    }

}
