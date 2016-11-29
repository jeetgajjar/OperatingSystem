package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.view.Log;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.ArrayList;

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


    public static void insertPCB(PCB to_insert){
        // TODO: insert into CPU or into ready/wait queue? this method seems unecessary
        // if all this method does is insert a pre-built pcb into the ready/wait queue,

        // Ready Queue takes care of memory usage
        ReadyQueue.enQueue(to_insert);
    }

    public static PCB removePCB(){
        // TODO: remove from CPU or from ready/wait queue? this method seems unncessary
        // if all this method does is remove pcb from the ready/wait queue,

        return ReadyQueue.deQueue();
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

    private static void buildReadyFromWaitQueue(){
        int number_moved = ReadyQueue.build();
        Log.i("Scheduler","Moved " + Integer.toString(number_moved) + " waiting processes to ready queue");
    }

    private static void buildExecutionFromReadyQueue(){
        int number_moved = ExecutionQueue.build();
        Log.i("Scheduler", "Moved " + Integer.toString(number_moved) + " ready processes to execution queue");
    }

    //TODO: log time that a particular process runs, ie get cpu time before and after each execution
    public static void runForNumberOfCycles(int number_of_cycles){
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
                    count += CPU.execute(current_process);
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
    public static void runTillEnd(){
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
                    if(current_process.io == current_process.cycles_left){
                        current_process.state = V.WAIT;
                        IOScheduler.scheduleIO(current_process);
                        break;
                    }
                    CPU.execute(current_process);
                }
                if(current_process.cycles_left == 0){
                    current_process.state = V.EXIT;
                }else if(current_process.state == V.RUN){
                    current_process.state = V.READY;
                    ExecutionQueue.enQueue(current_process);
                }
                current_process = ExecutionQueue.deQueue();
            }
        }
    }

    private static void schedule_execution_queue () {
        if (ExecutionQueue.isEmpty() && !WaitQueue.isEmpty()) {
            PCB current = WaitQueue.deQueue();
            if (current == null) {
                Log.e("Scheduler","Appears that the wait queue can't add a single process to execute on build");
            }
            while (current != null) {
                ExecutionQueue.enQueue(current);
                current = WaitQueue.deQueue();
            }
        }
    }

    public static void clean(){
        time_adjustment = 0;
    }

}
