package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

/**
 * Created by class on 10/13/16.
 */
public class IOScheduler {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static IOScheduler instance = new IOScheduler();
    private IOScheduler(){}
    public static IOScheduler getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    // called by penguin on a process that was in the cpu that now needs io
    public static void scheduleIO(PCB process_in){
        InterruptProcessor.addEvent(new ECB(
                InterruptProcessor.gen(),ECB.IO_INTERUPT,process_in
        ));
    }


    // gets called by ECB durring IO interupt
    public static int startIO(PCB process_in){
        int burst = IOBurst.generateIOBurst(process_in);
        if(burst > 0){
            process_in.state = PCB.READY;
            return burst;
        }
        if(burst < 0){
            //IO failure, add event back to queue
            InterruptProcessor.addEvent(new ECB(
                    InterruptProcessor.gen(),ECB.IO_INTERUPT,process_in
            ));
            return -1 * burst;
        }
        return 0;
    }
}
