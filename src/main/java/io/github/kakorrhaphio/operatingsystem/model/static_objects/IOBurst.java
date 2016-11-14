package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.view.Log;
import io.github.kakorrhaphio.operatingsystem.view.V;

/**
 * Created by class on 10/13/16.
 */
public class IOBurst {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static IOBurst instance = new IOBurst();
    private IOBurst(){}
    public static IOBurst getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO: log simulated randomized failure rates*/
    public static int generateIOBurst(PCB process_in){
        if(process_in.state != V.WAIT){
            Log.e("IOBurst","Process that is getting IO is not in waiting state");
            return 0;
        }
        if(CPU.random(0,10) < 2){
            int rTime = CPU.random(4,12);
            Log.i("IOBurst","Simulating IO Failure, cycles= " + Integer.toString(rTime));
            return -1 * rTime;
        }
        if(process_in.io > 0){
            Log.i("IOBurst","Performed IO Burst, cycles= " + Integer.toString(process_in.io));
            return process_in.io;
        }
        int rTime = CPU.random(7,15);
        Log.i("IOBurst","Performed IO Burst, cycles= " + Integer.toString(rTime));
        return rTime;
    }
}
