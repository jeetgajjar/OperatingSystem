package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import java.util.Comparator;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */
public class PCBComparator implements Comparator<PCB>{
    @Override
    public int compare(PCB x, PCB y){
        if(x.priority < y.priority){
            return -1;
        }
        if(x.priority > y.priority){
            return 1;
        }
        return 0;
    }

}
