package t2_p2;

import java.util.List;
import java.util.PriorityQueue;

public class OnlinePlanner extends PlanBuilder {
	PriorityQueue<Machine> mQueue;
	private int[] assignment;
	
	public OnlinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
		mQueue = new PriorityQueue<Machine>();
		for (Machine m : machines) {
			mQueue.add(m);
		}
	}

	@Override
	protected void plan() {
		assignment = new int[jobs.length];
		for (int i=0; i<jobs.length; i++) {
			assignToLessBusyMachine(i);
		}
	}
	
	private void assignToLessBusyMachine(int jobIndex) {
		Machine chosenM = mQueue.poll();
		assignment[jobIndex] = chosenM.num();
		chosenM.assignJob(jobs[jobIndex]);
		mQueue.add(chosenM);
	}

	@Override
	protected int[] getAssignment() {
		return assignment;
	}
}
