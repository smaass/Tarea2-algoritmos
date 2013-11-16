package t2_p2;

import java.util.Random;

public class Job {
	private static Random rand = new Random();
	private final int[] times;
	
	public Job(int[] times) {
		this.times = times;
	}
	
	public int time(int machineId) {
		return times[machineId];
	}
	
	public static Job randomJob(int machines) {
		int[] times = new int[machines];
		for(int i = 0; i < machines; i++){
			times[i] = rand.nextInt(99)+1;
		}
		return new Job(times);
	}
}
