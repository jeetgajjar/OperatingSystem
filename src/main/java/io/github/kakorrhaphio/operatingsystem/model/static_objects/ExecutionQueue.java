package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;
import io.github.kakorrhaphio.operatingsystem.view.Log;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by class on 10/13/16.
 */
public class ExecutionQueue {

    // example robin algorithm values
    private static final int FIRST_ROBIN_SIZE_LIMIT = 7;
    private static final int FIRST_ROBIN_AGE_LIMIT = 100;

    //TODO: include seperate kernal queue
    //TODO: make algorithm more stages


    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static ArrayList<PCB> robin_user; //three stages, short rr, long rr, fifo
    private static int robin_age;
    private static ExecutionQueue instance = new ExecutionQueue();
    private ExecutionQueue(){}
    public static ExecutionQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // build robin from priotity queue,
    // cleans ready
    public static void build(PriorityQueue<PCB> ready, boolean kernal){
        ArrayList<PCB> robin_user = new ArrayList();
        while(!ready.isEmpty()){
            robin_user.add(ready.poll());
        }
        robin_age = 0;
    }


    public static void enQueue(PCB to_add){
        if (robin_user == null){
            Log.e("ExecutionQueue","Attempting to enqueue to a null Kernal Execution Queue");
            return;
        }
        robin_user.add(to_add);
    }

    public static PCB deQueue(){
        if (robin_user == null){
            Log.e("ExecutionQueue","Attempting to dequeue from an empty Execution Queue");
            return null;
        }
        if (robin_user.isEmpty()){
            Log.i("ExecutionQueue","Execution Queue is empty, deleting instance");
            robin_user = null;
            return null;
        }
        return robin_user.remove(0);
    }

    // this makes the execution algorithm two stage...
    // first stage is round robin with 10 cycles per process
    // second stage is "fifo", -1 means complete process till finish
    public static int cycleAllocation(int current_process_cycles) {
        if (robin_user.size() < FIRST_ROBIN_SIZE_LIMIT || robin_age > FIRST_ROBIN_AGE_LIMIT){
            return current_process_cycles;
        } else{
            return 10;
        }
    }
}
