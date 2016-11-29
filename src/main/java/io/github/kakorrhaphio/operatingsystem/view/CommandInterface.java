package io.github.kakorrhaphio.operatingsystem.view;

import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.ECB;
import io.github.kakorrhaphio.operatingsystem.model.dynamic_objects.PCB;
import io.github.kakorrhaphio.operatingsystem.model.static_objects.*;

import java.io.*;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by class on 10/13/16.
 */
public final class CommandInterface {
    private static final int SCREEN_CLEAR = 50;
    private static final int HEADER_PADDING = 4;
    private static final int FOOTER_PADDING = 6;

    // Singleton * * * * * * * * * * * * * * * * * * * * * * * * * * *
    private static Date thisDate;
    private static String arg;
    private static Scanner in;
    private CommandInterface () {}
    private static CommandInterface instance = new CommandInterface();
    public static CommandInterface getInstance() {
        return instance;
    }
    // End Singleton * * * * * * * * * * * * * * * * * * * * * * * * *


    public static void start() {
        thisDate = new Date();
        in = new Scanner(System.in);
        loadScreen("NEW");
        int running = queryLoop(0);
        //while intent is not turn off intent i.e(intent == -1)
        while (running >= 0) {
            System.out.println("DEBUG: cut recursion");
            running = queryLoop(running);
        }
        in.close();
    }

    private static void loadScreen(String p){
        if(p.equals("NEW")){
            newline(SCREEN_CLEAR);
            System.out.println("PENGUIN");
            System.out.println("Enter any key...");
            System.out.println(thisDate.toString());
            newline(FOOTER_PADDING);
            in.nextLine();// waits till any command to go to home
        }else{
            newline(SCREEN_CLEAR);
            System.out.println("PENGUIN");
            System.out.println("Enter any key...");
            System.out.println(thisDate.toString());
            System.out.println("(reset)");
            newline(FOOTER_PADDING - 1);
            in.nextLine();// waits till any command to go to home
        }
    }

    public static int promptUser() {
        // clear screen
        newline(SCREEN_CLEAR);
        //HEADER
        //System.out.println(thisDate.toString());
        //HEADER PADDING
        newline(HEADER_PADDING);
        //BODY
        System.out.println("Welcome to the wonderful world of PENGUIN!");
        System.out.println(thisDate.toString());
        newline(FOOTER_PADDING);
        System.out.println("[P]ROC    [M]EM    [L]OAD    [E]XE    [R]ESET    E[X]IT");
        String[] temp = in.nextLine().toUpperCase().split(" ");
        if( temp.length == 2){
            arg = temp[1];
        } else{
            arg = "";
        }
        switch (temp[0]) {
            case "PROC":
                return 1;
            case "P":
                return 1;
            case "MEM":
                return 2;
            case "M":
                return 2;
            case "LOAD":
                return 3;
            case "L":
                return 3;
            case "EXE":
                return 4;
            case "E":
                return 4;
            case "RESET":
                return 5;
            case "R":
                return 5;
            case "EXIT":
                return 6;
            case "X":
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
        newline(SCREEN_CLEAR);
        // HEADER
        //System.out.println(thisDate.toString());
        //HEADER PADDING
        newline(HEADER_PADDING);
        // BODY

        System.out.println("PROC * * * * * * * * * * * * * * * * * * * * *");

        System.out.println("\n------Execution Queue------");
        String execQ = ExecutionQueue.printing();
        if (execQ.length() == 0){
            System.out.println("!!Empty");
        }else{
            System.out.print(execQ);
        }

        System.out.println("\n------Waiting Queue------");
        String waitQ = WaitQueue.printing();
        if (waitQ.length() == 0){
            System.out.println("!!Empty");
        }else{
            System.out.print(waitQ);
        }

        System.out.println("\n------Event Queue------");
        String eventQ = EventQueue.printing();
        if (eventQ.length() == 0){
            System.out.println("!!Empty");
        }else{
            System.out.print(eventQ);
        }

        // FOOTER PADDING
        newline(3);
        // FOOTER
        System.out.println("Press any key...");
        in.nextLine();
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
        in.nextLine();
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
            arg  = in.nextLine();
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
        in.nextLine();
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
        in.nextLine();
    }

    private static void reset(){
        CPU.clean();
        EventQueue.clean();
        ExecutionQueue.clean();
        InterruptProcessor.clean();
        IOBurst.clean();
        IOScheduler.clean();
        Scheduler.clean();
        WaitQueue.clean();
        loadScreen("RESET");
    }

    private static int exit(){
        newline(50);
        System.out.println("Are you sure?");
        System.out.println("YES    CANCEL");
        newline(4);
        String command = in.nextLine();
        if(command.toUpperCase().equals("YES") || command.toUpperCase().equals("Y")){
            return -1;
        }
        return 0;
    }

    private static void interpretFile(BufferedReader f){
        try {
            String line = f.readLine();
            while ( line != null){
                System.out.print(">>" + line);
                // parse line
                String[] parsed = line.split(" ");

                if (parsed.length > 0){
                    // cut spaces
                    int cur = 0;
                    while (parsed[0].length() == 0){
                        cur ++;
                    }
                    //cur points to first command
                    switch (parsed[cur]){
                        case "#":
                            System.out.println();
                        case "CALCULATE":
                            if (parsed.length == cur + 1) {
                                System.out.println(" <<ERROR>> No argument");
                            } else {
                                try {
                                    int a = Integer.parseInt(parsed[cur + 1]);
                                    Scheduler.insertPCB(ProcessManager.newCalculateProcess(a));
                                } catch (Exception e) {
                                    System.out.println(" <<ERROR>> Bad argument");
                                }
                            }
                        case "OUT":
                            Scheduler.insertPCB(ProcessManager.newOutProcess());
                            System.out.println();
                        case "I/O":
                            Scheduler.insertPCB(ProcessManager.newOutProcess());
                            System.out.println();
                        case "YIELD":
                            Scheduler.insertPCB(ProcessManager.newOutProcess());
                            System.out.println();
                        default:
                            System.out.println( "<<ERROR>> Bad command");
                    }
                }
                line = f.readLine();
            }
        } catch (IOException e){
            Log.e("CommandInterface","IO Exception in interpreter");
        }

    }

    public static void stdout (String to_print) {
        System.out.println(to_print);
    }

}
