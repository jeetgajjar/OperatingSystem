package io.github.kakorrhaphio.operatingsystem.model.static_objects;

/**
 * Created by class on 10/13/16.
 */
public class Penguin {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static Penguin instance = null;
    private Penguin(){}
    public static Penguin getInstance(){
        if(instance == null){
            instance = new Penguin();
            return instance;
        }
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void run(int cycles){
        if(cycles == 0){
            loopTillEnd();
        }else{
            leepTillCycle(cycles);
        }
        return;
    }

    private static void loopTillEnd(){

    }


}
