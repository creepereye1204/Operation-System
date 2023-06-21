

import java.util.*;

public class Sjf extends SchedulingAlgorithm {


    public Sjf(List<Process> processes, String algorithm) {
        super(processes, algorithm);
    }

    @Override
    protected void jsfSort(List<Process> readyQueue) {
        Collections.sort(readyQueue);
    }

    @Override
    protected void roundRobinSort(List<Process> readyQueue, int quantum) {

    }
}
