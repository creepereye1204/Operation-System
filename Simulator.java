import java.io.IOException;

public class Simulator {
    private final int step=1;
    private SchedulingAlgorithm schedulingAlgorithm;
    public Simulator(){
        this.schedulingAlgorithm=null;
    }

    public Simulator(final SchedulingAlgorithm schedulingAlgorithm) throws IOException {
        this.schedulingAlgorithm=schedulingAlgorithm;
        Main.reset(Main.path);
    }
    public void setSchedulingAlgorithm(final SchedulingAlgorithm schedulingAlgorithm){
        this.schedulingAlgorithm=schedulingAlgorithm;
    }

    public void runSimulation() throws IOException {
        runSimulation(1);
    }
    public void runSimulation(int quantum) throws IOException {
        Process.onJsf=false;
        Main.reset(Main.path);

        if(this.schedulingAlgorithm==null){
            System.out.println("Select Algorithm");
            return;
        }
        System.out.println(this.schedulingAlgorithm.getAlgotithm());

        this.schedulingAlgorithm.onSimulation(quantum);
        this.schedulingAlgorithm.showSummary(this.schedulingAlgorithm.terminatedProcesses);
        System.out.println("\n");
        Process.time=-1;
        Process.length=0;
        Process.cpuUtilization=0;
        Process.throughputPerHundredTime=0;

        Process.onJsf=false;


    }

}
