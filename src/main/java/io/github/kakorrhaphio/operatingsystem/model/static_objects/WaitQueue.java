package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;

import java.util.PriorityQueue;

/**
 * Created by class on 10/13/16.
 */
public class WaitQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static PriorityQueue<PCB> queue;
    private static WaitQueue instance = new WaitQueue();
    private WaitQueue(){
        queue = new PriorityQueue<>(10, new PCBComparator());
    }
    public static WaitQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(PCB to_add){
        queue.add(to_add);
    }

    public static PriorityQueue<PCB> getQueue(){
        return queue;
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
    }

}
