package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Date;

public class Main extends Application {

    static String from_exe = "";

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Welcome");
        GridPane grid = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(76);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(12);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(12);
        grid.getColumnConstraints().addAll(column1, column2, column3);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(15);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(75);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(5);
        RowConstraints row4 = new RowConstraints();
        row4.setPercentHeight(5);
        grid.getRowConstraints().addAll(row1, row2, row3, row4);

        //grid.setGridLinesVisible(true);



        Label log = new Label("Logging");
        log.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        log.setPrefWidth(1000);
        log.setPrefHeight(1000);
        grid.add(log, 0, 3);

        TextArea txt = new TextArea("penguins_are_cool > ");
        txt.setWrapText(true);
        txt.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                if (newValue.length() > 0) {
                    if (newValue.length() < 20) {
                        txt.setText("penguins_are_cool > ");
                    } else if (newValue.charAt(newValue.length() - 1) == '\n') {
                        String[] temp = newValue.split("\n");
                        String[] temp2 = temp[temp.length-1].split("penguins_are_cool > ");
                        if (temp2.length > 0) {
                            String comatose = command(temp2[temp2.length - 1], log);
                            if(comatose.equals("RESET")){
                                txt.setText("RESET!!!!!!\npenguins_are_cool > ");
                            }else {
                                txt.setText(newValue + comatose + "penguins_are_cool > ");
                            }
                        } else {
                            txt.setText(newValue + "penguins_are_cool > ");
                        }
                    } else {
                        String[] arg_array = newValue.split("\n");
                        if (arg_array[arg_array.length - 1].length() < 20) {
                            String output = "";
                            for (int i = 0; i < arg_array.length - 1; i ++) {
                                output += arg_array[i] + "\n";
                            }
                            txt.setText(output + "penguins_are_cool > ");
                        }
                    }
                }
            }
        });

        grid.add(txt, 0, 0, 1, 2);

        Label comm = new Label("     COMMANDS  -->  [P]ROC    [M]EM    [L]OAD    [E]XE    [R]ESET    E[X]IT");
        comm.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        comm.setPrefWidth(1000);
        comm.setPrefHeight(1000);
        grid.add(comm, 0, 2);


        Button bPower = new Button("Power");
        bPower.setPrefHeight(1000);
        bPower.setPrefWidth(1000);
        bPower.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                exiter();
            }
        });
        grid.add(bPower, 1,0);

        Button bReset = new Button("Reset");
        bReset.setPrefHeight(1000);
        bReset.setPrefWidth(1000);
        bReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reseter(txt, log);
            }
        });
        grid.add(bReset, 2,0);


        Task time_task = new Task() {
            @Override
            protected Object call() throws Exception {
                Date this_date;
                for (int i = 0; i < 10000; i++) {
                    this_date = new Date(System.currentTimeMillis());
                    Thread.sleep(1000);
                    updateValue(this_date.toString());
                }
                return null;
            }
        };
        Label time = new Label("Time Date");
        time.textProperty().bind(time_task.valueProperty());
        time.setPrefWidth(1000);
        time.setPrefHeight(1000);
        time.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        grid.add(time, 1, 2,2,2);

        new Thread(time_task).start();

        Text graphs = new Text("Graphs");
        grid.add(graphs, 1, 1);



        Scene scene = new Scene(grid, 1000, 618);
        primaryStage.setScene(scene);

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    private static String command(String argument, Label log) {
        if (argument.length() > 0) {
            argument = argument.toUpperCase();
            String[] arg_array = argument.split(" ");
            int cur = 0;
            while (cur < arg_array.length && arg_array[cur].length() == 0){
                cur ++;
            }
            if (cur >= arg_array.length) {
                return "INVALID ARGUMENT : \"" + argument + "\"\n\n";
            }
            switch (arg_array[cur]) {
                case "P":
                    return procer(log);
                case "PROC":
                    return procer(log);
                case "MEM":
                    return memer(log);
                case "M":
                    return memer(log);
                case "LOAD":
                    if (arg_array.length > cur + 1) {
                        return loader(arg_array[cur + 1],log);
                    }
                    return loader("",log);
                case "L":
                    if (arg_array.length > cur + 1) {
                        return loader(arg_array[cur + 1],log);
                    }
                    return loader("",log);
                case "EXE":
                    if (arg_array.length > cur + 1) {
                        return exeer(arg_array[cur + 1], log);
                    }
                    return exeer("", log);
                case "E":
                    if (arg_array.length > cur + 1) {
                        return exeer(arg_array[cur + 1], log);
                    }
                    return exeer("", log);
                case "RESET":
                    return reseter(log);
                case "R":
                    return reseter(log);
                case "EXIT":
                    return exiter();
                case "X":
                    return exiter();
                default:
                    return "INVALID ARGUMENT : \"" + argument + "\"\n\n";
            }
        }
        return "";
    }

    private static String procer(Label log) {
        long start = System.currentTimeMillis();


        String proc_output = "PROC * * * * * * * * * * * * * * * * * * * * *";

        proc_output += "\n\n------Execution Queue------\n";
        String execQ = ExecutionQueue.printing();
        if (execQ.length() == 0){
            proc_output += "\tEmpty!!";
        }else{
            proc_output += execQ;
        }

        proc_output += "\n\n------Waiting Queue------\n";
        String waitQ = WaitQueue.printing();
        if (waitQ.length() == 0){
            proc_output += "\tEmpty!!";
        }else{
            proc_output += waitQ;
        }

        proc_output += "\n\n------Event Queue------\n";
        String eventQ = EventQueue.printing();
        if (eventQ.length() == 0){
            proc_output += "\tEmpty!!";
        }else{
            proc_output += eventQ;
        }


        log.setText("Successfully ran \"PROC\" in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");
        return proc_output + "\n\n";
    }

    private static String memer(Label log) {
        long start = System.currentTimeMillis();
        String mem_output = MemoryManager.printing();
        log.setText("Successfully ran \"MEM\" in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");
        return mem_output + "\n\n";
    }

    private static String loader(String input_file, Label log) {
        long start = System.currentTimeMillis();
        String load_output = "LOAD * * * * * * * * * * * * * * * * * * * * *\n\n";
        if (input_file.length() == 0) {
            load_output += "PLEASE SPECIFY WHICH \"PS\" FILE TO LOAD:\n\n";
            File folder = new File(System.getProperty("user.dir"));
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                String name = listOfFiles[i].getName();
                if (listOfFiles[i].isFile() && name.charAt(name.length()-2) == 'p' && name.charAt(name.length()-1) == 's') {
                    load_output += "->" + listOfFiles[i].getName() + "\n";
                }
            }
            return load_output + "\n\n\n";
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input_file));
            load_output += "SUCCESSFULLY LOADED FILE \"" + input_file + "\":\n\n";
            load_output += interpretFile(reader);
            reader.close();
        } catch (FileNotFoundException e){
            return load_output + "COULD NOT FIND FILE \"" + input_file + "\"\n\n\n";
        } catch (IOException e){
            //Log.e("CommandInterface", "IO Exception in load function");
        }

        log.setText("Successfully ran \"LOAD\" in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");
        return load_output + "\n\n";
    }

    private static String exeer(String args , Label log) {
        long start = System.currentTimeMillis();
        from_exe = "";
        int number_of_cycles_to_execute = Integer.MAX_VALUE;
        //System.out.println("hey! >" + args + "<");
        try {
            if (args.length() > 0) {
                number_of_cycles_to_execute = Integer.parseInt(args);
            }
        } catch (Exception e) {
            return "ARGUMENTS WERE UNDEFINED\n" + e.toString() + "\n";
        }
        Scheduler.run(number_of_cycles_to_execute);
        log.setText("Successfully ran \"EXE\" in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");
        return "EXECUTING *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *\n\n" + from_exe + "\n\n";
    }

    private static String reseter(Label log) {
        long start = System.currentTimeMillis();
        CPU.clean();
        EventQueue.clean();
        EventManager.clean();
        ExecutionQueue.clean();
        InterruptProcessor.clean();
        IOBurst.clean();
        IOScheduler.clean();
        MemoryManager.clean();
        ProcessManager.clean();
        Scheduler.clean();
        WaitQueue.clean();
        log.setText("Successfully \"RESET\" system in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");

        return "RESET";
    }

    private static String reseter(TextArea txt, Label log) {
        long start = System.currentTimeMillis();
        CPU.clean();
        EventQueue.clean();
        EventManager.clean();
        ExecutionQueue.clean();
        InterruptProcessor.clean();
        IOBurst.clean();
        IOScheduler.clean();
        MemoryManager.clean();
        ProcessManager.clean();
        Scheduler.clean();
        WaitQueue.clean();
        log.setText("Successfully \"RESET\" system in " + Long.toString(System.currentTimeMillis() - start) + " milliseconds");
        txt.setText("RESET!!!!!!\npenguins_are_cool > ");
        return "RESET";
    }

    private static String exiter() {
        System.exit(0);
        //return "PROCESSING : EXIT\n\n";
        return "";
    }

    private static String interpretFile(BufferedReader f) {
        //System.out.println("here");
        String to_return = "";
        try {
            String line = f.readLine();
            //System.out.println("here1");
            int count = 1;
            while ( line != null){
                //System.out.print("|>" + line);
                to_return += Integer.toString(count) + "\t|> " + line + " <|               ";
                // parse line
                String[] parsed = line.split(" ");
                if (parsed.length > 0){
                    //cur points to first command
                    //System.out.print(" " + Integer.toString(count) + " (((" + parsed[0] + "))) ");
                    if (parsed[0].equals("")) {
                        to_return += "\n";
                    }else if (parsed[0].equals("#")) {
                        to_return += "\n";
                    }else if (parsed[0].equals("CALCULATE")) {
                        if (parsed.length == 1) {
                            to_return += " <<ERROR>> No argument\n";
                        } else {
                            try {
                                int a = Integer.parseInt(parsed[1]);
                                Scheduler.insertPCB(ProcessManager.new_CALCULATE_process(a));
                                to_return += " <<SUCCESS>>\n";
                            } catch (Exception e) {
                                to_return += " <<ERROR>> Undefined argument\n";
                            }
                        }
                    }else if (parsed[0].equals("OUT")) {
                        Scheduler.insertPCB(ProcessManager.new_OUT_process());
                        to_return += " <<SUCCESS>>\n";
                    }else if (parsed[0].equals("I/O")) {
                        Scheduler.insertPCB(ProcessManager.new_IO_process());
                        to_return += " <<SUCCESS>>\n";
                    }else if (parsed[0].equals("YIELD")) {
                        //Scheduler.insertPCB(ProcessManager.new_YIELD_process());
                        to_return += " <<SUCCESS>>\n";
                    }else {
                        to_return += " <<ERROR>> Undefined\n";
                    }


                }
                line = f.readLine();
                count ++;
            }
        } catch (IOException e){
            //Log.e("CommandInterface","IO Exception in interpreter");
        }
        return to_return;
    }
}
