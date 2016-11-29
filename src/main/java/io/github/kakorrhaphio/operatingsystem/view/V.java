package io.github.kakorrhaphio.operatingsystem.view;

import static io.github.kakorrhaphio.operatingsystem.model.static_objects.EventManager.IO_INTERRUPT;
import static io.github.kakorrhaphio.operatingsystem.model.static_objects.EventManager.YIELD_INTERRUPT;

/**
 * Created by JuliusCeasar on 11/11/2016.
 *
 * V class stores all public constant and toString methods
 */
public class V {

    // Process states
    public static final int NEW = 0;
    public static final int READY = 1;
    public static final int RUN = 2;
    public static final int WAIT = 3;
    public static final int EXIT = 4;


    // Kernel bit
    public static final int USER = 0;
    public static final int KERNEL = 1;



    // Process state toString
    public static String pstate(int state){
        switch (state) {
            case NEW:
                return "NEW";
            case READY:
                return "READY";
            case RUN:
                return "RUN";
            case WAIT:
                return "WAIT";
            case EXIT:
                return "EXIT";
            default:
                Log.e("V","Process state toString method called with non-state parameter");
                return "";
        }
    }

    // Event state toString
    public static String estate(int state){
        switch (state) {
            case IO_INTERRUPT:
                return "IO_INTERRUPT";
            case YIELD_INTERRUPT:
                return "YIELD_INTERRUPT";
            default:
                Log.e("V","Event state toString method called with non-state parameter");
                return "";
        }
    }

    // Kernel bit toString
    public static String kernelbit(int kernelbit){
        switch (kernelbit) {
            case USER:
                return "USER";
            case KERNEL:
                return "KERNEL";
            default:
                Log.e("V","Kernel bit toString method called with non-kernel parameter");
                return "";
        }
    }
}
