package t2_p2;

//import java.util.ArrayList;
//import java.util.Comparator;
import java.util.List;
//import java.util.PriorityQueue;

public class OnlinePlanner extends PlanBuilder {
//	private int[] assignment;
	
	public OnlinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
	}

	@Override
	protected void plan() {
//		assignment = new int[jobs.length];
		for (int i=0; i<jobs.length; i++) {
			assignToAllMachines(i);
		}
	}
	
	private void assignToAllMachines(int jobIndex) {
		Job job = jobs[jobIndex];
		for(Machine m : machines){
			m.assignJob(job);
		}
	}

	@Override
	protected int[][] getAssignment() {
		return null;
	}
}
