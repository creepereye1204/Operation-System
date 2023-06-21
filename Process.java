
import java.util.*;
public class Process implements Comparable<Process>{
	
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int gettotalCpuTime() {
		return totalCpuTime;
	}
	public void settotalCpuTime(int totalCpuTime) {
		this.totalCpuTime = totalCpuTime;
	}
	public int getCpuBurst() {
		return cpuBurst;
	}
	public void setCpuBurst(int cpuBurst) {
		this.cpuBurst = cpuBurst;
	}
	public int getIoBurst() {
		return ioBurst;
	}
	public void setIoBurst(int ioBurst) {
		this.ioBurst = ioBurst;
	}
	public int getRunningTime() {return runningTime;}
	public void setRunningTime(int runningTime) {this.runningTime = runningTime;}
	public int getPid() {return pid;}
	public boolean getCpuMode() {return this.cpuMode;}

	public void setCpuMode(boolean cpuMode) {this.cpuMode = cpuMode;}
	public int getCpuTime() {return this.cpuTime;}

	public void setCpuTime(int cpuTime) {this.cpuTime = cpuTime;}

	public int getIoTime() {return ioTime;}

	public void setIoTime(int ioTime) {this.ioTime = ioTime;}
	public void setIoCount(int ioTime) {this.ioTime = ioTime;}
	public int getIoCount() {return this.getIoCount();}
	public int getFinishedTime() {return finishedTime;}

	public int getWaitingTime() {return waitingTime;}

	public void setWaitingTime(int waitingTime) {this.waitingTime = waitingTime;}

	public void setFinishedTime(int finishedTime) {this.finishedTime = finishedTime;}
	public int getTurnAroundTime() {return turnAroundTime;}

	public void setTurnAroundTime(int turnAroundTime) {this.turnAroundTime = turnAroundTime;}

	private int arrivalTime;
	private int totalCpuTime;
	private int cpuBurst;
	private int ioBurst;
	private int runningTime;

	private int ioCount;


	private int pid;
	private int cpuTime=0;
	private int ioTime=0;
	private int finishedTime=0;




	private int turnAroundTime=0;
	private int waitingTime=0;
	private boolean cpuMode;
	private Random rand;



	static int time=-1;
	static int length=0;
	static float cpuUtilization=0;
	static float throughputPerHundredTime=0;

	static boolean onJsf=false;


	Process(int arrivalTime, int totalCpuTime, int cpuBurst, int ioBurst){

		rand = new Random();
		this.pid=++length;
		this.cpuMode=true;
		this.arrivalTime=arrivalTime;
		this.totalCpuTime=totalCpuTime;
		this.cpuBurst=rand.nextInt(cpuBurst)+1;
		this.ioBurst=ioBurst>0?rand.nextInt(ioBurst)+1:0;
		this.ioCount=0;
		this.runningTime=0;
	}

	@Override
	public int compareTo(Process o) {
		if(!Process.onJsf){
			return getArrivalTime()-o.getArrivalTime();
		}
		int timeRemaining=gettotalCpuTime()-getRunningTime();
		int otherTimeRemaining=o.gettotalCpuTime()-o.getRunningTime();
		return timeRemaining-otherTimeRemaining;
	}

}
