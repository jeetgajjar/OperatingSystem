package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECBComparator;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCBComparator;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by class on 10/13/16.
 */
public class ReadyQueue {
    public static int FREE_MEMORY;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static PriorityQueue<PCB> queue;
    private static ReadyQueue instance = new ReadyQueue();
    private ReadyQueue(){
        queue = new PriorityQueue<>(10, new PCBComparator());
        FREE_MEMORY = V.MEMORY_LIMIT;
    }
    public static ReadyQueue getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // build robin from priotity queue,
    // cleans ready
    public static int build(){
        int count = 0;
        PCB temp = WaitQueue.deQueue(FREE_MEMORY);
        while(temp != null){
            FREE_MEMORY -= temp.memory;
            queue.add(temp);
            count ++;
            temp = WaitQueue.deQueue(FREE_MEMORY);
        }
        return count;
    }

    // Takes care of memory usage
    public static void enQueue(PCB to_add){
        if (to_add.memory <= FREE_MEMORY) {
            queue.add(to_add);
        } else {
            WaitQueue.enQueue(to_add);
        }
    }

    public static PCB deQueue(){
        if (isEmpty()) {
            return null;
        }
        return queue.poll();
    }

    public static Object[] printingArray(){
        if (queue == null){
            return null;
        }
        return queue.toArray();
    }


    public static boolean isEmpty(){
        return queue.isEmpty();
    }

}
