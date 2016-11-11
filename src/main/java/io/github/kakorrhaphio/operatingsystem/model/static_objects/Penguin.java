package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

/**
 * Created by class on 10/13/16.
 */
public class Penguin {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static Penguin instance = null;
    private Penguin(){}
    public static Penguin getInstance(){
        if(instance == null){
            instance = new Penguin();
            return instance;
        }
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void run(int cycles){
        if(cycles == 0){
            loopTillEnd();
        }else{
            loopTillCycle(cycles);
        }
        return;
    }

    private static void loopTillEnd(){

    }

    //TODO: log time that a particular process runs, ie get cpu time before and after each execution
    private static void loopTillCycle(int number_of_cycles){
        int count = 0;
        while(count < number_of_cycles){
            // call scheduler to build execution queue from ready queue
            // also look at wait queue

            Scheduler.buildExecutionQueue();

            //TODO: put "build execution queue" into a pcb with kernal bit


            // iterate through round robin till empty
            PCB current_process = ExecutionQueue.deQueue();
            while(current_process != null){
                current_process.state = PCB.RUN;
                int alloted_time = ExecutionQueue.currentCycleAllocated;
                if (alloted_time == -1){
                    alloted_time = current_process.cycles - current_process.current_cycle; // third stage of round robin, fifo
                }
                for(int i = 0; i < alloted_time; i++){
                    if(InterruptProcessor.hasInterrupt()){
                        current_process.state = PCB.READY;
                        ExecutionQueue.enQueue(current_process);
                        count += InterruptProcessor.getEvent().run();
                        break;
                    }
                    if(count >= number_of_cycles){
                        current_process.state = PCB.READY;
                        ExecutionQueue.enQueue(current_process);
                        break;
                    }
                    if(current_process.current_cycle >= current_process.cycles){
                        current_process.state = PCB.EXIT;
                        break;
                    }
                    if(current_process.needsIO == current_process.current_cycle){
                        current_process.state = PCB.WAIT;
                        IOScheduler.scheduleIO(current_process);
                        break;
                    }
                    count ++;
                    CPU.execute(current_process);
                }
                if(current_process.state == PCB.RUN){
                    current_process.state = PCB.READY;
                    ExecutionQueue.enQueue(current_process);
                }
                ExecutionQueue.updateTimeAllocation();
                current_process = ExecutionQueue.deQueue();
            }
        }
    }
}
