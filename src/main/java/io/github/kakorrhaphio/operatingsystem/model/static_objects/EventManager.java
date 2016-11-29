package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.view.V;

import java.util.concurrent.Callable;

/**
 * Created by JuliusCeasar on 11/18/2016.
 */
public class EventManager {
    public static int eid_head = 0;
    public static final int IO_INTERRUPT = 0;
    public static final int YIELD_INTERRUPT = 1;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static EventManager instance = new EventManager();
    private EventManager() {}
    public static EventManager getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    // builds IO block
    public static ECB new_IO_event (PCB process_in, int time_in) {
        return new ECB (IO_INTERRUPT,process_in,1, time_in);
    }

    // builds YIELD block
    public static ECB new_YIELD_event () {
        ECB new_ecb = new ECB (YIELD_INTERRUPT,null,1,0);
        InterruptProcessor.signalInterrupt(new_ecb);
        return new_ecb;
    }

    // standard print statement for all ECBs
    public static String toString(ECB e_in){
        String out = "[";
        out += Integer.toString(e_in.eID) + ";";
        out += V.estate(e_in.event) + ";";
        out += Integer.toString(e_in.process.pid) + ";";
        out += Integer.toString(e_in.priority) + "]";
        return out;
    }

    public static int gen_eid(){
        eid_head ++;
        return eid_head;
    }
}
