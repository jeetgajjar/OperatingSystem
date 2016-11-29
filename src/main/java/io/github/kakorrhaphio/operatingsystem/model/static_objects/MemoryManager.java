package io.github.kakorrhaphio.operatingsystem.model.static_objects;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;

import java.util.ArrayList;

/**
 * Created by JuliusCeasar on 11/19/2016.
 */
public class MemoryManager {
    public static final int MEMORY_LIMIT = 256000;
    private static int ALLOCATED_MEMORY = 0;
    private static final ArrayList<String> memory = new ArrayList<>();

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static MemoryManager instance = new MemoryManager();
    private MemoryManager() {}
    public static MemoryManager getInstance(){
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *

    public static boolean add_process (PCB process) {
        if (MEMORY_LIMIT - ALLOCATED_MEMORY - process.memory_size < 0) {
            return false;
        }
        ALLOCATED_MEMORY += process.memory_size;
        process.memory_head = memory.size();
        memory.add("");
        return true;
    }

    public static void remove_process (int pointer) {
        if (pointer >= 0 && pointer < memory.size()) {
            ALLOCATED_MEMORY -= memory.remove(pointer).length();
        }
    }

    public static void append_data (int pointer, String new_data) {
        if (pointer >= 0 && pointer < memory.size()) {
            memory.set(pointer,memory.get(pointer) + new_data);
        }
    }

    public static void write_data (int pointer, String new_data) {
        if (pointer >= 0 && pointer < memory.size()) {
            memory.set(pointer, new_data);
        }
    }

    public static String get_data (int pointer) {
        if (pointer >= 0 && pointer < memory.size()) {
            return memory.get(pointer);
        }
        return "";
    }
}
