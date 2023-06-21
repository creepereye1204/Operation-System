

import java.util.*;

public class RoundRobin extends SchedulingAlgorithm {


    public RoundRobin(List<Process> processes, String algorithm) {
        super(processes, algorithm);
    }

    @Override
    protected void jsfSort(List<Process> readyQueue) {

    }

    @Override
    protected void roundRobinSort(List<Process> readyQueue, int quantum) {
        if(Process.time%quantum==0 && !readyQueue.isEmpty()){
            readyQueue.add(readyQueue.remove(0));
        }
    }




}
