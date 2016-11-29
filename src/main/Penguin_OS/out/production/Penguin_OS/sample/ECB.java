package sample;



/**
 * Created by JuliusCeasar on 11/7/2016.
 */
public class ECB {
    public int eID;
    public int event;
    public PCB process;
    public int priority;
    public long time;


    public ECB(int event, PCB process, int priority, int time){
        this.eID = EventManager.gen_eid();
        this.event = event;
        this.process = process;
        this.priority = priority;
        this.time = time + System.currentTimeMillis();
    }

    public int run(){
        switch (this.event){
            case EventManager.IO_INTERRUPT :
                IOScheduler.startIO(this.process.memory_head);
                return CPU.random(10,15); //cycles it takes to move io to buffer
            case EventManager.YIELD_INTERRUPT :
                //Log.i("ECB","Performed yield interrupt, cycles= 1");
                return 1;
            default:
                //Log.e("ECB","When interrupt run, event is undefined --> this.event= '" + Integer.toString(this.event) + "'");
                return 0;
        }
    }
}
