package t2_p2;

import java.util.LinkedList;

public class Machine implements Comparable<Machine> {
	private final int num;
	private LinkedList<Job> jobs;
	
	public Machine(int num) {
		this.num = num;
		jobs =  new LinkedList<Job>();
	}
	
	public void assignJob(Job j) {
		SpaceInfo space = findSpace(j);
		if (space != null) {
			putJobInSpace(space, j);
		}
		else {
			putJobAtEnd(j);
		}
	}
	
	public void putJobInSpace(SpaceInfo space, Job j) {
		j.setStartAt(space.start, num);
		jobs.add(space.index, j);
	}
	
	public void putJobAtEnd(Job j) {
		int startTime = 0;
		if (!jobs.isEmpty()) {
			startTime = jobs.getLast().endTime(num);
		}
		j.setStartAt(startTime, num);
		jobs.add(j);
	}
	
	public SpaceInfo findSpace(Job j) {
		if (jobs.size() <= 1) return null;
		Job prevJob = jobs.get(0);
		for (int i=1; i<jobs.size(); i++) {
			Job nextJob = jobs.get(i);
			int minJobTime = j.minNextTime();
			int space = nextJob.startAt(num) - prevJob.endTime(num);
			int spaceFromMin = nextJob.startAt(num) - minJobTime;
			if (spaceFromMin >= j.time(num) && spaceFromMin <= space) {
				return new SpaceInfo(i, nextJob.startAt(num) - spaceFromMin, spaceFromMin);
			}
			prevJob = nextJob;
		}
		return null;
	}
	
	// Ultimo tiempo ocupado por la maquina
	public int time() {
		if (jobs.isEmpty()) return 0;
		return jobs.getLast().endTime(num);
	}
	
	public void clear() {
		for (Job j : jobs) {
			j.clear(num);
		}
		jobs.clear();
	}
	
	public int num() {
		return num;
	}
	
	public class SpaceInfo {
		public final int index;
		public final int start;
		public final int size;
		
		public SpaceInfo(int index, int start, int size) {
			this.index = index;
			this.start = start;
			this.size = size;
		}
	}

	@Override
	public int compareTo(Machine m) {
		return time() - m.time();
	}
}
