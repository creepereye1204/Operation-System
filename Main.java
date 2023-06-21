import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {
	public static List<Process> processes;
	public final static String path="testcase.txt";
	final static int bufferSize=1024*16;
	public static void reset(final String path) throws IOException {
		Process.time=-1;
		Process.length=0;
		Process.cpuUtilization=0;
		Process.throughputPerHundredTime=0;
		Process.onJsf=false;
		processes=new ArrayList<>();



		BufferedReader reader = new BufferedReader(
				new FileReader(path),bufferSize
		);
		String str=reader.readLine();

		reader.close();

		String[] processedStr=str.replaceAll("[^0-9]"," ").replaceAll("  "," ").split(" ");
		int[] processedIntArray= Stream.of(processedStr).mapToInt(Integer::parseInt).toArray();
		for(int i=1;i<processedIntArray[0]*4;i+=4){
			final int arrivalTime=processedIntArray[i];
			final int totalCpuTime=processedIntArray[i+1];
			final int cpuBurst=processedIntArray[i+2];
			final int ioBurst=processedIntArray[i+3];
			processes.add(new Process(arrivalTime,totalCpuTime,cpuBurst,ioBurst));
		}
	}
	public static void main(String[] args) throws IOException {
		reset(Main.path);

		Simulator simulator = new Simulator(new Fcfs(processes,Fcfs.class.getName()));
		simulator.runSimulation();
		simulator.setSchedulingAlgorithm(new RoundRobin(processes,RoundRobin.class.getName()));
		simulator.runSimulation(1);

		simulator.setSchedulingAlgorithm(new RoundRobin(processes,RoundRobin.class.getName()));
		simulator.runSimulation(10);


		simulator.setSchedulingAlgorithm(new RoundRobin(processes,RoundRobin.class.getName()));
		simulator.runSimulation(100);


		simulator.setSchedulingAlgorithm(new Sjf(processes,Sjf.class.getName()));
		simulator.runSimulation();



	}

}
