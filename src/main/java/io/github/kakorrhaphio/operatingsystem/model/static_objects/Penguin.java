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

    private static void loopTillCycle(int number_of_cycles){
        int count = 0;
        while(count < number_of_cycles){
            // call scheduler to build execution queue from ready queue
            // also look at wait queue
            Scheduler.buildExecutionQueue();

            // iterate through round robin till empty
            PCB current_process = ExecutionQueue.deQueue();
            while(current_process != null){
                current_process.state = PCB.RUN;
                for(int i = 0; i < ExecutionQueue.currentCycleAllocated; i++){
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
                current_process = ExecutionQueue.deQueue();
            }
        }
    }
}
