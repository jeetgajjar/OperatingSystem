package io.github.kakorrhaphio.operatingsystem.model.static_objects;

/**
 * Created by class on 10/13/16.
 */
public class IOBurst {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static IOBurst instance = new IOBurst();
    private IOBurst(){}
    public static IOBurst getInstance(){ return instance; }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /*TODO:
    generateIOBurst()
     */
}
