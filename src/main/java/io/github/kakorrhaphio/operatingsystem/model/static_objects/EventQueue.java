package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECBComparator;

import java.util.PriorityQueue;

/**
 * Created by class on 10/13/16.
 */
public class EventQueue {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static PriorityQueue<ECB> queue;
    private static EventQueue instance = new EventQueue();
    private EventQueue(){
        queue = new PriorityQueue<>(10, new ECBComparator());
    }
    public static EventQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static void enQueue(ECB to_add){
        queue.add(to_add);
    }

    public static ECB deQueue(){
        return queue.poll();
    }

    public static void clean(){
        queue = new PriorityQueue<>(10, new ECBComparator());
    }
}
