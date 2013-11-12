package t2;

import java.util.Random;

public class Job {
	private static Random rand = new Random();
	private final int time;
	
	public Job(int time) {
		this.time = time;
	}
	
	public int time() {
		return time;
	}
	
	public static Job randomJob() {
		return new Job(rand.nextInt(99)+1);
	}
}
