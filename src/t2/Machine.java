package t2;


public class Machine implements Comparable<Machine> {
	private int machineNum;
	private int busyTime;
	
	public Machine(int num) {
		busyTime = 0;
		machineNum = num;
	}
	
	public void assignJob(Job j) {
		busyTime += j.time();
	}
	
	public int time() {
		return busyTime;
	}
	
	public void clear() {
		busyTime = 0;
	}
	
	public int num() {
		return  machineNum;
	}

	@Override
	public int compareTo(Machine m) {
		return this.busyTime - m.busyTime;
	}
}
