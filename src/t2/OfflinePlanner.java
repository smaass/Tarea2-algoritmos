package t2;

import java.util.List;

public class OfflinePlanner extends PlanBuilder {
	private int[] assignment;

	public OfflinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
	}
	
	@Override
	protected void plan() {
		int makeSpan = Integer.MAX_VALUE;
		AssignmentGenerator gen = new AssignmentGenerator(machines.length, jobs.length);
		assignment = new int[jobs.length];
		
		while (gen.hasNext()) {
			int[] order = gen.nextArrangement();
			clearMachines();
			assignToMachines(order);
			
			int localMakespan = makespan();
			if (localMakespan < makeSpan) {
				makeSpan = localMakespan;
				System.arraycopy(order, 0, assignment, 0, order.length);
			}
		}
		clearMachines();
		assignToMachines(assignment);
	}
	
	private void assignToMachines(int[] assignment) {
		for (int i=0; i<assignment.length; i++) {
			machines[assignment[i]].assignJob(jobs[i]);
		}
	}
	
	private void clearMachines() {
		for (Machine m : machines) {
			m.clear();
		}
	}

	@Override
	protected int[] getAssignment() {
		return assignment;
	}
	
	private class AssignmentGenerator {
		private int[] genArray;
		private int machines;
		private int jobs;
		private boolean finished;
		
		public AssignmentGenerator(int machines, int jobs) {
			assert(machines > 0 && jobs > 0);
			this.machines = machines;
			this.jobs = jobs;
		}
		
		public boolean hasNext() {
			return !finished;
		}
		
		public int[] nextArrangement() {
			mutateArray();
			return genArray;
		}
		
		private synchronized void mutateArray() {
			if (genArray == null) {
				initArray();
				return;
			}
			
			for (int i=genArray.length-1; i>=0; i--) {
				if (i == 0) {
					finished = true;
				}
				else if (genArray[i] + 1 < machines) {
					genArray[i]++;
					break;
				}
				else {
					genArray[i] = 0;
				}
			}
		}
		
		private void initArray() {
			this.genArray = new int[jobs];
		}
	}
}
