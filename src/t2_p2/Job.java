package t2_p2;

import java.util.Random;

public class Job {
	private static Random rand = new Random();
	private final int[] times;
	private final int[] startAt;
	
	public Job(int[] times, int machines) {
		this.times = times;
		this.startAt = new int[machines];
		for (int i=0; i<machines; i++) startAt[i] = -1;
	}
	
	public int time(int machineId) {
		return times[machineId];
	}
	
	public static Job randomJob(int machines) {
		int[] times = new int[machines];
		for(int i = 0; i < machines; i++){
			times[i] = rand.nextInt(99)+1;
		}
		return new Job(times, machines);
	}
	
	public int startAt(int m) {
		return startAt[m];
	}
	
	public void setStartAt(int start, int machine) {
		startAt[machine] = start;
	}
	
	public int endTime(int m) {
		return startAt[m] + times[m];
	}
	
	public void clear(int m) {
		startAt[m] = -1;
	}
	
	// Tiempo minimo al cual se puede agregar una proxima etapa de este trabajo
	public int minNextTime() {
		int maxStart = 0;
		for (int s : startAt) {
			if (maxStart < s) maxStart = s;
		}
		return maxStart;
	}
}
