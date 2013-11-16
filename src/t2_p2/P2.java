package t2_p2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P2 {
	public static final String experimentsFolder = "resultsP2/";
	
	public static void main(String[] args) {
		System.out.println("Experimentando...");
		experiments();
		System.out.println("Fin :D");
	}
	
	public static void experiments() {
		for (int i=8; i<=16; i+=2){
			experiment(4, i, 400);
		}
	}
	
	public static void experiment(int machines, int jobsNum, int runs) {
		try {
			String filename = experimentsFolder + "E" + machines + "M" + jobsNum + "J" + ".mat";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			int[] line = new int[2];
			for (int i=0; i<runs; i++) {
				List<Job> jobs = randomJobsList(jobsNum, machines);
				comparePlans(machines, jobs, line);
				writer.write(line[0] + " " + line[1] + "\n");
			}
			writer.close();
			System.out.println("Listo " + filename + "!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void comparePlans(int machines, List<Job> jobs, int[] output) {
		Plan offline = new OfflinePlanner(machines, jobs).getPlan();
		Plan online = new OnlinePlanner(machines, jobs).getPlan();
		output[0] = offline.getMakespan();
		output[1] = online.getMakespan();
	}
	
	public static List<Job> randomJobsList(int size, int machines) {
		List<Job> jobs = new ArrayList<Job>();
		for (int i=0; i<size; i++) {
			jobs.add(Job.randomJob(machines));
		}
		return jobs;
	}
}
