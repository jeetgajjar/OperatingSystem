package sample;

/**
 * Created by class on 10/13/16.
 */
public class InterruptProcessor {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static boolean interrupt;
    private static ECB current_event = null;
    private static InterruptProcessor instance = new InterruptProcessor();
    private InterruptProcessor(){
        interrupt = false;
    }
    public static InterruptProcessor getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void signalInterrupt(ECB block_in){
        interrupt = true;
        current_event = block_in;
    }


    public static void addEvent(ECB event_in){
        EventQueue.enQueue(event_in);
        signalInterrupt(EventQueue.deQueue());
    }


    public static ECB are_there_any_interrupts () {
        if (interrupt) {
            return current_event;
        }
        return null;
    }

    public static void clean(){
        interrupt = false;
        current_event = null;
    }
}
