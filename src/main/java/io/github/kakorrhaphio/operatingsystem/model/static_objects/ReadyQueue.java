package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;

import java.util.PriorityQueue;

/**
 * Created by class on 10/13/16.
 */
public class ReadyQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    public static int currentCycleAllocated;
    private static PriorityQueue<PCB> queue;
    private static ReadyQueue instance = new ReadyQueue();
    private ReadyQueue(){
        queue = new PriorityQueue<>(10, new PCBComparator());
    }
    public static ReadyQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(PCB to_add){
        queue.add(to_add);
    }

}
