package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import io.github.kakorrhaphio.operatingsystem.model.static_objects.IOBurst;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.IOScheduler;
import io.github.kakorrhaphio.operatingsystem.view.Log;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */
public class ECB {

    public static final int IO_INTERUPT = 1;
    public static final int YIELD_INTERRUPT = 2;

    public int eID;
    public int event;
    public PCB process;

    /**
     *
     * @param id
     * @param event
     * @param process
     */
    public ECB(int id, int event, PCB process){
        this.eID = id;
        this.event = event;
        this.process = process;
    }
    public int run(){
        switch (this.event){
            case IO_INTERUPT :
                return IOScheduler.startIO(this.process);
            case YIELD_INTERRUPT :
                Log.i("ECB","Performed yield interrupt, cycles= 1");
                return 1;
            default:
                Log.e("ECB","When interrupt run, event is undefined --> this.event= '" + Integer.toString(this.event) + "'");
                return 0;
        }
    }
}
