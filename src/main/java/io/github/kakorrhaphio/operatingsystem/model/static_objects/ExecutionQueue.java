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


    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public static int currentCycleAllocated;
    private static ArrayList<PCB> robin_user; //three stages, short rr, long rr, fifo
    private static ArrayList<PCB> robin_kernal; // always fifo
    private static int robin_age;
    private static ExecutionQueue instance = new ExecutionQueue();
    private ExecutionQueue(){}
    public static ExecutionQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // build robin from priotity queue
    public static void build(PriorityQueue<PCB> ready, boolean kernal){
        ArrayList<PCB> new_robin = new ArrayList();
        while(!ready.isEmpty()){
            new_robin.add(ready.poll());
        }
        robin_age = 0;
        updateTimeAllocation();
    }


    public static void enQueue(PCB to_add, boolean kernal){
        if (kernal){
            if (robin_kernal == null){
                Log.e("ExecutionQueue","Attempting to enqueue to a null Kernal Execution Queue");
                return;
            }
            robin_kernal.add(to_add);
        }else{
            if (robin_user == null){
                Log.e("ExecutionQueue","Attempting to enqueue to a null Kernal Execution Queue");
                return;
            }
            robin_user.add(to_add);
        }
    }

    public static PCB deQueue(){
        if (robin == null){
            Log.e("ExecutionQueue","Attempting to dequeue from an empty Execution Queue");
            return null;
        }
        if (robin.isEmpty()){
            Log.i("ExecutionQueue","Execution Queue is empty, deleting instance");
            robin = null;
            return null;
        }
        return robin.remove(0);
    }

    public static void updateTimeAllocation() {
        // need to detirmine when to change current cycle allocated based on robin age, and robin size
        /**
        if (robin.size() > FIRST_ROBIN_SIZE_LIMIT) {
            if (robin_age > FIRST_ROBINT_AGE_LIMIT){

            }
            return;
        }else if (robin.size() > SECOND_ROBIN_LIMIT){
            currentCycleAllocated = 20;
            return;
        }else{
            currentCycleAllocated = -1;
        }
         */
    }
}
