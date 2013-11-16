package t2_p2;

import java.util.ArrayList;
import java.util.List;


public class Machine implements Comparable<Machine> {
	private int machineNum;
	private List<Range> busyTimes;
	private int maxBusyTime;
	
	public Machine(int num) {
		machineNum = num;
		maxBusyTime = 0;
		busyTimes = new ArrayList<Range>();
	}
	
	public void assignJob(Job j, int stage) {
		int timeInMachine = j.time(machineNum);
		int minTime = 0;
		Range lastRange = null;
		for(Range r : busyTimes){
			if (r.start() - minTime <= timeInMachine) {
				// Se agrega job en un espacio de la maquina!
				maxBusyTime = minTime + timeInMachine;
				busyTimes.add(new Range(minTime, maxBusyTime));
				return;
			}
			minTime = r.end();
			lastRange = r;
		}
		// Se agrega job al final de la maquina!
		busyTimes.add(new Range(lastRange.end()+1, lastRange.end()+1 + timeInMachine));
	}
	
	public int time() {
		return maxBusyTime;
	}
	
	public void clear() {
		maxBusyTime = 0;
		busyTimes = new ArrayList<Range>();
	}
	
	public int num() {
		return  machineNum;
	}

	@Override
	public int compareTo(Machine m) {
		return this.maxBusyTime - m.maxBusyTime;
	}
}
