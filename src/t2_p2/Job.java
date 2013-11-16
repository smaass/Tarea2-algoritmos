package t2_p2;

import java.util.ArrayList;
import java.util.Random;

public class Job {
	private static Random rand = new Random();
	private final int[] times;
	private ArrayList<Range> busyTimes;
	
	public Job(int[] times) {
		this.times = times;
		this.busyTimes = new ArrayList<Range>();
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

	public ArrayList<Range> busyTimes() {
		return busyTimes;
	}
	
	public boolean fitInRange(int machine, Range machineRange) {
		Range reducedRange = new Range(machineRange.start(), machineRange.end());
		for (Range r : busyTimes) {
			reducedRange.substract(r);
		}
		if (reducedRange.length() > time(machine)){
			return true;
		}
		return false;
	}

	public void addBusyTime(Range range) {
		busyTimes.add(range);
	}
}
