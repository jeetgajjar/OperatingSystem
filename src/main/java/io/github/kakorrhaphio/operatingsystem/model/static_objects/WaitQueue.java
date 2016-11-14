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

    // will only dequeue if there is enough memory to add current block
    // TODO: add second priority queue that sorts by size, then it could add small (less important blocks)
    public static PCB deQueue(int free_memory){
        if (isEmpty()) {
            return null;
        }
        PCB temp = queue.peek();
        if (temp.memory > free_memory) {
            return null;
        }
        return queue.poll();
    }

    public static PriorityQueue<PCB> getQueue(){
        return queue;
    }

    public static boolean isEmpty(){
        return queue.isEmpty();
    }

}
