package io.github.kakorrhaphio.operatingsystem.view;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by class on 10/13/16.
 */
public class CommandInterface {
    private static final int SCREEN_CLEAR = 50;
    private static final int HEADER_PADDING = 4;
    private static final int FOOTER_PADDING = 6;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static Date thisDate;
    private static String arg;
    private static Scanner in;
    private CommandInterface() {
    }
    private static CommandInterface instance = new CommandInterface();
    public static CommandInterface getInstance() {
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void start() {
        thisDate = new Date();
        in = new Scanner(System.in);
        newline(50);
        System.out.println("PENGUIN");
        System.out.println("Enter any key...");
        System.out.println(thisDate.toString());
        newline(3);
        in.next();// waits till any command to go to home
        int running = queryLoop(0);
        // will power off in 5 minutes
        long start = System.currentTimeMillis();
        //while intent is not turn off intent i.e(intent == -1)
        while (running >= 0) {
            System.out.println("DEBUG: cut recursion");
            running = queryLoop(running);
        }
        in.close();
    }

    public static int promptUser() {
        // clear screen
        newline(SCREEN_CLEAR);
        //HEADER
        System.out.println(thisDate.toString());
        //HEADER PADDING
        newline(HEADER_PADDING);
        //BODY
        System.out.println("Welcome to the wonderful world of PENGUIN!");
        System.out.println(thisDate.toString());
        newline(FOOTER_PADDING);
        System.out.println("PROC    MEM    LOAD    EXE    RESET    EXIT");
        String[] temp = in.next().toUpperCase().split(" ");
        if( temp.length == 2){
            arg = temp[1];
        } else{
            arg = "";
        }
        switch (temp[0]) {
            case "PROC":
                return 1;
            case "MEM":
                return 2;
            case "LOAD":
                return 3;
            case "EXE":
                return 4;
            case "RESET":
                return 5;
            case "EXIT":
                return 6;
            default:
                return 0;
        }
    }

    private static void newline(int n) {
        for (int i = 0; i < n; i++) {
            System.out.println();
        }
    }

    private static int queryLoop(int command_pairings) {
        // goes from welcome screen to prompt screen
        switch (command_pairings) {
            case 0:
                return promptUser();
            case 1:
                proc();
                return 0;
            case 2:
                mem();
                return 0;
            case 3:
                load();
                return 0;
            case 4:
                exe();
                return 0;
            case 5:
                reset();
                return 0;
            case 6:
                return exit();
            default:
                return 0;

        }
    }

    private static void proc(){
        // clear screen
        newline(50);
        // HEADER
        //System.out.println(thisDate.toString());
        //HEADER PADDING
        newline(3);
        // BODY
        System.out.println("PROC * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("\n--------Ready Queue--------");
        Object[] readyQ = ReadyQueue.printingArray();
        if (readyQ == null || readyQ.length == 0){
            System.out.println("!!Empty");
        } else {
            for (int i = 0; i < readyQ.length; i++) {
                System.out.println(((PCB) readyQ[i]).toString());
            }
        }
        System.out.println("\n------Execution Queue------");
        Object[] execQ = ExecutionQueue.printingArray();
        if (execQ == null  || execQ.length == 0){
            System.out.println("!!Empty");
        }else{
            for(int i = 0; i < execQ.length; i++){
                System.out.println(((PCB)execQ[i]).toString());
            }
        }
        System.out.println("\n------Waiting Queue------");
        Object[] waitQ = ExecutionQueue.printingArray();
        if (waitQ == null  || waitQ.length == 0){
            System.out.println("!!Empty");
        }else{
            for(int i = 0; i < waitQ.length; i++){
                System.out.println(((PCB)waitQ[i]).toString());
            }
        }
        System.out.println("\n------Event Queue------");
        Object[] eventQ = ExecutionQueue.printingArray();
        if (eventQ == null  || eventQ.length == 0){
            System.out.println("!!Empty");
        }else{
            for(int i = 0; i < eventQ.length; i++){
                System.out.println(((ECB)eventQ[i]).toString());
            }
        }
        // FOOTER PADDING
        newline(3);
        // FOOTER
        System.out.println("Press any key...");
        in.next();
    }

    private static void mem(){
        newline(50);
        // add process calls
        System.out.println("MEM * * * * * * * * * * * * * * * * * * * * *");
        System.out.println("\nMemory usage\n\n");
        System.out.print("Memory used: " + Integer.toString(V.MEMORY_LIMIT - ReadyQueue.FREE_MEMORY));
        System.out.print(" Bytes, " + Integer.toString(100 - ((int)(100 * ReadyQueue.FREE_MEMORY / V.MEMORY_LIMIT))));
        System.out.print("%\t\t\t\t\t\t\t\t\t\t\t");
        System.out.print("Memory free: " + Integer.toString(ReadyQueue.FREE_MEMORY));
        System.out.print(" Bytes, " + Integer.toString((int)(100 * ReadyQueue.FREE_MEMORY / V.MEMORY_LIMIT)));
        System.out.print("%\n");
        for(int i = 0; i < 100; i++){
            System.out.print("_");
        }
        System.out.println();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 100 - ((int)(100 * ReadyQueue.FREE_MEMORY / V.MEMORY_LIMIT)); j++){
                System.out.print("#");
            }
            for(int j = 0; j < (int)(100 * ReadyQueue.FREE_MEMORY / V.MEMORY_LIMIT); j++){
                System.out.print(" ");
            }
            System.out.println("|");
        }
        newline(4);
        System.out.println("Enter any key...");
        in.next();
    }

    private static void load(){
        newline(50);
        // add process calls
        System.out.println("LOAD * * * * * * * * * * * * * * * * * * * * *\n\nfiles:");
        if(arg.length() == 0){
            //list directory
            File folder = new File(System.getProperty("user.dir"));
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("> " + listOfFiles[i].getName());
                }
            }
            System.out.print("\nPlease specify the file here: ");
            arg  = in.next();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(arg));
            System.out.println("\n\nLoaded in file \"" + arg + "\" into memory");
            interpretFile(reader);
            reader.close();
        } catch (FileNotFoundException e){
            System.out.println("\n\nCould not find file \"" + arg + "\" in directory");
        } catch (IOException e){
            Log.e("CommandInterface", "IO Exception in load function");
        }
        newline(4);
        System.out.println("Enter any key...");
        in.next();
    }

    private static void exe(){
        newline(50);
        // add process calls
        System.out.println("EXE * * * * * * * * * * * * * * * * * * * * *");
        if(arg.length() == 0){
            Scheduler.runTillEnd();
        }else{
            Scheduler.runForNumberOfCycles(Integer.parseInt(arg));
        }

        newline(4);
        System.out.println("Enter any key...");
        in.next();
    }

    private static void reset(){
        newline(50);
        // add process calls
        System.out.println("RESETTING IN...");
        newline(4);
        System.out.print(Integer.toString(4));
        for(int i = 3; i > 0; i--){
            System.out.print("\r" + Integer.toString(i) + " seconds");
            try{
                Thread.sleep(1100);
            } catch(InterruptedException e){

            }
        }
        CPU.clean();
        EventQueue.clean();
        ExecutionQueue.clean();
        InterruptProcessor.clean();
        IOBurst.clean();
        IOScheduler.clean();
        ReadyQueue.clean();
        Scheduler.clean();
        WaitQueue.clean();
    }

    private static int exit(){
        newline(50);
        System.out.println("Are you sure?");
        System.out.println("YES    CANCEL");
        newline(4);
        String command = in.next();
        if(command.toUpperCase().equals("YES")){
            return -1;
        }
        return 0;
    }

    private static void interpretFile(BufferedReader f){
        try {
            String line = f.readLine();
            while ( line != null){
                System.out.println(">>" + line);
                // parse line
                String[] parsed = line.split(" ");
                for (int i = 0; i < parsed.length; i++){
                    if(parsed[i].length() > 0){
                        //priority, cycles, memory, io, kernel_bit, Callable<Integer> run
                        if (parsed[i].charAt(0) == '#'){
                            break;
                        } else if (parsed[i].charAt(0) == 'C' && parsed.length > i - 1){
                            //CALCULATE
                            Scheduler.insertPCB(new PCB(
                                    4,
                                    Integer.parseInt(parsed[i + 1]),
                                    Integer.parseInt(parsed[i + 1]) * 100,
                                    -1,
                                    0,
                                    null
                            ));
                            break;
                        } else if (parsed[i].charAt(0) == 'I'){
                            //I/O
                            break;
                        } else if (parsed[i].charAt(0) == 'Y'){
                            //YIELD
                            break;
                        } else if (parsed[i].charAt(0) == 'O'){
                            //OUT
                            Scheduler.insertPCB(new PCB(
                                    2,
                                    1,
                                    100,
                                    -1,
                                    0,
                                    new Callable<Integer>() {
                                        @Override
                                        public Integer call() throws Exception {
                                            System.out.println("Hello from inside the machine");
                                            return 0;
                                        }
                                    }
                            ));
                            break;
                        }
                    }
                }


                line = f.readLine();
            }
        } catch (IOException e){
            Log.e("CommandInterface","IO Exception in interpreter");
        }

    }

}
