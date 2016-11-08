package io.github.kakorrhaphio.operatingsystem.model.dynamic_objects;

import java.util.Comparator;

/**
 * Created by JuliusCeasar on 11/7/2016.
 */
public class ECBComparator implements Comparator<ECB>{
    @Override
    public int compare(ECB x, ECB y){
        if(x.priority < y.priority){
            return -1;
        }
        if(x.priority > y.priority){
            return 1;
        }
        return 0;
    }

}
