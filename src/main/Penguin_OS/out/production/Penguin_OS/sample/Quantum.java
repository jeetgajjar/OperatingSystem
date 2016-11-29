package sample;


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
        if (code != null) {
            try {
                String output[] = code.call();
                if (pointer >= 0 && output.length > 0) {
                    switch (output[0]) {
                        case "a":
                            MemoryManager.append_data(pointer, output[1]);
                        case "w":
                            MemoryManager.write_data(pointer, output[1]);
                        case "p":
                            Main.from_exe += MemoryManager.get_data(pointer);
                        case "io":
                            IOScheduler.scheduleIO(this.owner, Integer.parseInt(output[2]));
                        default:
                            //Log.e("Quantum","Callable is outputting bad arguments");
                    }
                }
            } catch (Exception e) {
                //Log.e("Quantum", "Quantum callable threw exception --> \n" + e.toString());
            }
            code = null;
        }
    }
}
