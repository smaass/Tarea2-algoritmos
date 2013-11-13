package t2;

import java.util.List;

public abstract class PlanBuilder {
	protected Machine[] machines;
	protected Job[] jobs;
	
	public PlanBuilder(int numMachines, List<Job> jobs) {
		machines = new Machine[numMachines];
		for (int i=0; i<numMachines; i++) {
			machines[i] = new Machine(i);
		}
		this.jobs = new Job[jobs.size()];
		jobs.toArray(this.jobs);
	}
	
	public Plan getPlan() {
		plan();
		return new Plan(getAssignment(), makespan());
	}
	
	protected int makespan() {
		int makeSpan = 0;
		for (Machine m: machines) {
			if (m.time() > makeSpan) makeSpan = m.time();
		}
		return makeSpan;
	}
	
	/**
	 * Assigns each job to a machine
	 */
	protected abstract void plan();
	
	/**
	 * Returns assignment as array of ints, where each index represents a job
	 * and each value a machine's number.
	 */
	protected abstract int[] getAssignment();
}
