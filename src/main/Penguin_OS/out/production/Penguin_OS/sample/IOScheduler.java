package sample;

/**
 * Created by class on 10/13/16.
 */
public class IOScheduler {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static IOScheduler instance = new IOScheduler();
    private IOScheduler () {}
    public static IOScheduler getInstance () {
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    // called by penguin on a process that was in the cpu that now needs io
    public static void scheduleIO (PCB process_in, int time) {
        InterruptProcessor.addEvent(EventManager.new_IO_event(process_in,time));
    }

    // gets called by ECB durring IO interupt
    public static void startIO(int buffer_pointer){
        IOBurst.generateIOBurst(buffer_pointer);
    }

    public static void clean(){
        // nothing
    }
}
