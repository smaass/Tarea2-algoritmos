package t2_p2;

import java.util.List;

public class OfflinePlanner extends PlanBuilder {
	private int[][] assignment;

	public OfflinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
	}
	
	@Override
	protected void plan() {
		int makeSpan = Integer.MAX_VALUE;
		AssignmentGenerator gen = new AssignmentGenerator(machines.length, jobs.length);
		assignment = new int[jobs.length][machines.length];
		
		while (gen.hasNext()) {
			int[][] order = gen.nextArrangement();
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
	
	private void assignToMachines(int[][] assignment) {
		for (int i=0; i<assignment.length; i++) {
			for (int j=0; j<assignment[i].length; j++) {
				machines[assignment[i][j]].assignJobStage(jobs[i], j);
			}
		}
	}
	
	private void clearMachines() {
		for (Machine m : machines) {
			m.clear();
		}
	}

	@Override
	protected int[][] getAssignment() {
		return assignment;
	}
	
	private class AssignmentGenerator {
		private int[][] permutations;
		private int[][] genArray;
		private int[] permIndex;
		private int machines;
		private int jobs;
		private boolean finished;
		
		public AssignmentGenerator(int machines, int jobs) {
			assert(machines > 0 && jobs > 0);
			this.machines = machines;
			this.jobs = jobs;
			permutations = Utils.permutationsArray(machines);
		}
		
		public boolean hasNext() {
			return !finished;
		}
		
		public int[][] nextArrangement() {
			mutateArray();
			return genArray;
		}
		
		private synchronized void mutateArray() {
			if (genArray == null) {
				initArray();
				return;
			}
			
			for (int i=permIndex.length-1; i>=0; i--) {
				if (i == 0 && permIndex[0] == machines-1) {
					finished = true;
				}
				else if (permIndex[i] + 1 < machines) {
					permIndex[i]++;
					genArray[i] = permutations[permIndex[i]];
					break;
				}
				else {
					permIndex[i] = 0;
					genArray[i] = permutations[permIndex[i]];
				}
			}
		}
		
		private void initArray() {
			this.permIndex = new int[jobs];
			this.genArray = new int[jobs][];
			for (int i=0; i<jobs; i++) {
				genArray[i] = permutations[permIndex[i]];
			}
		}
	}
}
