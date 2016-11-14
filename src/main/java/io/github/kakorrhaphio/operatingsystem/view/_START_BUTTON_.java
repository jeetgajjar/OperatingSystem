package io.github.kakorrhaphio.operatingsystem.view;

/**
 * Created by JuliusCeasar on 11/13/2016.
 */
public class _START_BUTTON_ {
    public static void main(String[] args){
        if (args.length == 0){
            // default
            runInTerminal();
        }else if(args.length == 1){
            if (args[0].equals("FX")){
                //TODo: change runInTerminal() method to runInFX() once FX is built
                runInTerminal();
            }
            if (args[0].equals("TERMINAL")){
                runInTerminal();
            }
        } else{
            System.out.print("::: Too many arguments");
        }
    }

    private static void runInTerminal(){
        //TODo: change the name of command interface to terminal, to differentiat it from fx
        CommandInterface.start();
    }
}
