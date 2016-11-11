package io.github.kakorrhaphio.operatingsystem.view;

import io.github.kakorrhaphio.operatingsystem.model.static_objects.CPU;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.Scheduler;

import java.util.ArrayList;

/**
 * Created by class on 10/13/16.
 */
public class CommandInterface {
    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private CommandInterface(){}
    private static CommandInterface instance = new CommandInterface();
    public static CommandInterface getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    /* TODO
    proc()
    mem()
    load()
    exe()
    reset()
    promptUser()
     */
}
