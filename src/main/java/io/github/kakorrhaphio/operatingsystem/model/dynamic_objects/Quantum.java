package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import io.github.kakorrhaphio.operatingsystem.model.static_objects.IOScheduler;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.InterruptProcessor;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.MemoryManager;
import io.github.kakorrhaphio.operatingsystem.view.CommandInterface;
import io.github.kakorrhaphio.operatingsystem.view.Log;

import java.util.concurrent.Callable;

/**
 * Created by JuliusCeasar on 11/18/2016.
 */
public class Quantum {
    private int quantum_cycle_size;
    private Callable<String[]> code;
    private PCB owner;

    public Quantum (int cycles_in, Callable<String[]> code_in, PCB owner) {
        quantum_cycle_size = cycles_in;
        code = code_in;
        this.owner = owner;
    }

    public int size () {
        return this.quantum_cycle_size;
    }

    public void run (int pointer) {
        try {
            String output[] = code.call();
            if (pointer >= 0 && output.length > 0) {
                switch (output[0]) {
                    case "a":
                        MemoryManager.append_data(pointer, output[1]);
                    case "w":
                        MemoryManager.write_data(pointer, output[1]);
                    case "p":
                        CommandInterface.stdout(MemoryManager.get_data(pointer));
                    case "io":
                        IOScheduler.scheduleIO(this.owner,Integer.parseInt(output[2]));
                    default:
                        Log.e("Quantum","Callable is outputting bad arguments");
                }
            }
        } catch (Exception e) {
            Log.e("Quantum", "Quantum callable threw exception --> \n" + e.toString());
        }
        code = null;
    }
}
