package sample;

import java.util.ArrayList;

/**
 * Created by JuliusCeasar on 11/19/2016.
 */
public class MemoryManager {
    public static final int MEMORY_LIMIT = 256000;
    private static int ALLOCATED_MEMORY = 0;
    private static final ArrayList<memory_block> memory = new ArrayList();

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
        ALLOCATED_MEMORY += get_block(process);
        return true;
    }

    private static int get_block(PCB process_in) {
        for (int i = 0; i < memory.size(); i ++) {
            memory_block cur = memory.get(i);
            if (cur.free && cur.size >= process_in.memory_size) {
                process_in.memory_head = i;
                cur.free = false;
                cur.process = process_in;
                cur.data = "";
                return cur.size;
            }
        }
        process_in.memory_head = memory.size();
        memory.add(new memory_block(memory.size(),process_in.memory_size,process_in,""));
        return process_in.memory_size;
    }

    public static void defrag () {
        ArrayList<memory_block> df_list = new ArrayList<>();
        int df_size = 0;
        for (int i = 0; i < memory.size(); i ++) {
            memory_block cur = memory.get(i);
            if (!cur.free) {
                df_list.add(cur);
                df_size += cur.size;
            }
        }
        memory.clear();
        for (int i = 0; i < df_list.size(); i ++) {
            memory_block cur = df_list.get(i);
            cur.process.memory_head = i;
            cur.beginning = i;
            memory.add(cur);
        }
        ALLOCATED_MEMORY = df_size;
    }

    public static void remove_process (int pointer) {
        if (pointer >= 0 && pointer < memory.size()) {
            System.out.println("removing " + Integer.toString(pointer));
            ALLOCATED_MEMORY -= memory.get(pointer).size;
            memory.get(pointer).free = true;
        }
    }

    public static void append_data (int pointer, String new_data) {
        System.out.println("append");
        if (pointer >= 0 && pointer < memory.size()) {
            memory.get(pointer).data += new_data;
        }
    }

    public static void write_data (int pointer, String new_data) {
        if (pointer >= 0 && pointer < memory.size()) {
            memory.get(pointer).data = new_data;
        }
    }

    public static String get_data (int pointer) {
        if (pointer >= 0 && pointer < memory.size()) {
            return memory.get(pointer).data;
        }
        return "";
    }

    public static void clean() {
        ALLOCATED_MEMORY = 0;
        memory.clear();
    }

    public static String printing () {
        //System.out.println("About to scrub");
        //scrub();
        System.out.println("About to defrag");
        defrag();
        String to_return = "MEM * * * * * * * * * * * * * * * * * * ";
        to_return += "\n\n Total Memory = " + Integer.toString(MEMORY_LIMIT) + " Bytes";
        to_return +=   "\n Memory Used  = " + Integer.toString(ALLOCATED_MEMORY) + " Bytes";
        to_return +=   "\n Memory Free  = " + Integer.toString(MEMORY_LIMIT - ALLOCATED_MEMORY) + " Bytes\n\n";
        to_return += "Allocated Memory Blocks:\n";
        if ( memory.size() == 0) {
            to_return += "0 -- > " + Integer.toString(MEMORY_LIMIT) + " : [free]";
        }else {
            int last = 0;
            for (int i = 0; i < memory.size(); i ++) {
                to_return += Integer.toString(last) + " -- > " + Integer.toString(last + memory.get(i).size) +
                        " : " + getBlock(i) + "\n";
                last += memory.get(i).size;
            }
            to_return += Integer.toString(last) + " -- > " + Integer.toString(MEMORY_LIMIT) + " : [free]";
        }


        to_return += "\n";
        return to_return;
    }

    private static void scrub () {
        Object[] exQ = ExecutionQueue.get_processes();
        Object[] waQ = WaitQueue.get_processes();
        Object[] evQ = EventQueue.get_processes();
        System.out.println("About to look at mem");
        for (int i = 0; i < memory.size(); i ++) {
            System.out.println("loop 1");
            int cur = memory.get(i).process.pid;
            boolean check = true;
            for (int j = 0; j < exQ.length && check; j++) {
                System.out.println("loop 2");
                if (((PCB)exQ[j]).pid == cur) {
                    ((PCB)exQ[j]).state = ProcessManager.READY;
                    check = false;
                }
            }
            for (int j = 0; j < waQ.length && check; j++) {
                System.out.println("loop 3");
                if (((PCB)waQ[j]).pid == cur) {
                    ((PCB)waQ[j]).state = ProcessManager.WAIT;
                    check = false;
                }
            }
            for (int j = 0; j < evQ.length && check; j++) {
                System.out.println("loop 4");
                if (((ECB)evQ[j]).process.pid == cur) {
                    ((ECB)evQ[j]).process.state = ProcessManager.WAIT;
                    check = false;
                }
            }
            if (check) {
                memory.get(i).free = true;
            }
        }
        Object[] mQ = memory.toArray();
        for (int i = 0; i < evQ.length; i++) {
            System.out.println("loop 5");
            PCB cur = (PCB)evQ[i];
            boolean check = true;
            for (int j = 0; j < evQ.length && true; j++) {
                System.out.println("loop 6");
                if (((memory_block)mQ[j]).process.pid == cur.pid) {
                    check = false;
                }
            }
            if (check) {
                ALLOCATED_MEMORY += get_block(((ECB)evQ[i]).process);
            }
        }
    }

    private static String getBlock(int p) {
        memory_block b = memory.get(p);
        if (b.free) {
            return "free";
        }
        return ProcessManager.toString(b.process);
    }



    private static class memory_block{
        int beginning;
        int size;
        PCB process;
        String data;
        boolean free;
        int next;

        public memory_block(int beg, int siz, PCB pro, String data) {
            this.beginning = beg;
            this.size = siz;
            this.process = pro;
            this.data = data;
            this.free = false;
            this.next = -1;
        }
    }
}
