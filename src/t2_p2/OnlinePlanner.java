package t2_p2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class OnlinePlanner extends PlanBuilder {
	private PriorityQueue<Machine> queue;
	
	public OnlinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
		queue = new PriorityQueue<Machine>();
		
		for(Machine m : machines){
			queue.add(m);
		}
	}

	@Override
	protected void plan() {
		for (int i=0; i<jobs.length; i++) {
			assignToAllMachines(i);
		}
	}
	
	private void assignToAllMachines(int jobIndex) {
		Job job = jobs[jobIndex];
		int size = machines.length;

		ArrayList<Machine> auxMachines = new ArrayList<Machine>(size);
		Machine m;
		
		while(( m = queue.poll()) != null){
			m.assignJob(job);
			auxMachines.add(m);
		}
		
		for(int i = 0; i < size ; i++) {
			queue.add(auxMachines.get(i));
		}
	}

	@Override
	protected int[][] getAssignment() {
		return null;
	}
}
