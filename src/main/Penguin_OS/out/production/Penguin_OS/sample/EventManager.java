package sample;


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
        out += estate(e_in.event) + ";";
        out += Integer.toString(e_in.process.pid) + ";";
        out += Integer.toString(e_in.priority) + "]";
        return out;
    }

    public static int gen_eid(){
        eid_head ++;
        return eid_head;
    }

    public static void clean() {
        eid_head = 1;
    }

    // Event state toString
    public static String estate(int state){
        switch (state) {
            case IO_INTERRUPT:
                return "IO_INTERRUPT";
            case YIELD_INTERRUPT:
                return "YIELD_INTERRUPT";
            default:
                //Log.e("V","Event state toString method called with non-state parameter");
                return "";
        }
    }
}
