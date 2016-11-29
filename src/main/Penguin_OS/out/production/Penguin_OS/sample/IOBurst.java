package sample;

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
    public static void generateIOBurst(int buffer){
        MemoryManager.write_data(buffer, "This is a simulated IO burst");
    }

    public static void clean(){
        // nothing
    }
}
