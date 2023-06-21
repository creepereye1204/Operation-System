
import java.io.IOException;
import java.util.*;
public abstract class SchedulingAlgorithm {
	protected final int step=1;
	protected int quantum;
	protected String algorithm;
	protected List<Process> processes;
	protected List<Process> readyQueue= new ArrayList<>();
	protected List<Process> terminatedProcesses= new ArrayList<>();
	public SchedulingAlgorithm(List<Process> processes,String algorithm) {

		// TODO Auto-generated constructor stub
		Collections.sort(processes);
		this.processes=processes;
		this.algorithm=algorithm;

	}
	protected abstract void jsfSort(List<Process> readyQueue);
	protected abstract void roundRobinSort(List<Process> readyQueue,int quantum);

	public void onSimulation(int quantum) {
		// TODO Auto-generated method stub
		this.quantum=quantum;
		Process.onJsf=true;

		while(Process.length>this.terminatedProcesses.size()) {
			//step 0 time is gone while terminatedProcessList is completely full
			Process.time += step;

			//step 1 check current readyQueue
			if(!this.readyQueue.isEmpty() && this.readyQueue.get(0).gettotalCpuTime() > this.readyQueue.get(0).getRunningTime()) {






				//step 2 switch mode to I/O Burst or CPU Burst
				Process currentProcess = this.readyQueue.get(0);
				if(currentProcess.getCpuMode()){

					currentProcess.setCpuTime(currentProcess.getCpuTime()+step);

					if(currentProcess.getIoBurst()!=0 && currentProcess.getCpuTime()%currentProcess.getCpuBurst()==0){
						currentProcess.setCpuMode(false);
					}
					this.readyQueue.get(0).setRunningTime(currentProcess.getRunningTime() + step);
				}
				else {
					currentProcess.setIoTime(currentProcess.getIoTime()+step);

					if(currentProcess.getIoBurst()!=0 && currentProcess.getIoTime()%currentProcess.getIoBurst()==0){
						currentProcess.setCpuMode(true);
					}
				}


			}
			//step 3 put process into terminatedProcessList that finished Process
			if (!this.readyQueue.isEmpty() && this.readyQueue.get(0).getRunningTime() >= this.readyQueue.get(0).gettotalCpuTime()) {

				Process process=this.readyQueue.remove(0);
				int finishedTime=Process.time;
				int turnaroundTime=finishedTime-process.getArrivalTime();

				process.setTurnAroundTime(turnaroundTime);

				process.setFinishedTime(finishedTime);
				this.terminatedProcesses.add(process);
				if(!this.readyQueue.isEmpty()){
					Process newProcess=this.readyQueue.get(0);
					newProcess.setWaitingTime(Process.time-newProcess.getArrivalTime());
					jsfSort(this.readyQueue);
				}

			}

			//step 4 if process is arrival then put into ReadyQueue
			while (!this.processes.isEmpty() && this.processes.get(0).getArrivalTime() <= Process.time) {
				Process process=processes.remove(0);

				this.readyQueue.add(process);

			}

			roundRobinSort(this.readyQueue,this.quantum);
		}

	}



	protected void showSummary(List<Process> finishedProcess) {
		float averageThroughputPerHundredTime=0;
		float cpuUtilization=0;
		float averageTurnaroundTime =0;
		float averageWaitingTime =0;
		System.out.println("This scheduling was started at 0");
		for(Process process:finishedProcess){
			System.out.println("");
			System.out.println("pid:"+process.getPid());
			System.out.println("arrivalTime:"+process.getArrivalTime());
			System.out.println("ioBurstTime:"+process.getIoBurst());
			System.out.println("cpuBurstTime:"+process.getCpuBurst());
			System.out.println("finishedTime:"+process.getFinishedTime());
			System.out.println("turnAroundTime:"+process.getTurnAroundTime());
			System.out.println("cpuTime:"+process.getCpuTime());
			System.out.println("ioTime:"+process.getIoTime());
			System.out.println("waitingTime:"+(process.getTurnAroundTime()-process.getIoTime()-process.getCpuTime()));
			System.out.println("");
			averageTurnaroundTime+=(float) process.getTurnAroundTime();
			averageWaitingTime+=(float) process.getTurnAroundTime()-process.getIoTime()-process.getCpuTime();
			cpuUtilization+=(float) process.getCpuTime();

		}
		averageTurnaroundTime/=Process.length;
		averageWaitingTime/=Process.length;
		cpuUtilization= (float) (100.0*(cpuUtilization/Process.time));
		averageThroughputPerHundredTime=  ((float)Process.length/(float)Process.time);
		final float ioUtilization= (float) (100.0-cpuUtilization);

		System.out.println("summary");
		System.out.println("This scheduling was finished at "+Process.time);
		System.out.println("This scheduling has cpuUtilization of "+cpuUtilization+"%");
		System.out.println("This scheduling has ioUtilization of "+ioUtilization+"%");
		System.out.println("This scheduling has averageThroughputPerHundredTime of "+averageThroughputPerHundredTime);
		System.out.println("This scheduling has averageTurnaroundTime of "+averageTurnaroundTime);
		System.out.println("This scheduling has averageWaitingTime of "+averageWaitingTime);
	}

	public String getAlgotithm(){return this.algorithm;}
}
