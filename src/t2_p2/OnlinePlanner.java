package t2_p2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class OnlinePlanner extends PlanBuilder {
	PriorityQueue<Machine> mQueueSpaces;
	PriorityQueue<Machine> mQueueTimes;
	
	private int[] assignment;
	
	public OnlinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);

		mQueueSpaces = new PriorityQueue<Machine>(numMachines, new MachineComparatorBySpace());
		mQueueTimes = new PriorityQueue<Machine>(numMachines, new MachineComparatorByTime());

		for (Machine m : machines) {
			mQueueSpaces.add(m);
			mQueueTimes.add(m);
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
		Job job = jobs[jobIndex];
		
		ArrayList<Machine> sortedMachines = new ArrayList<Machine>();
		Machine spaceMachine;
		while((spaceMachine = mQueueSpaces.poll()) != null) {
			sortedMachines.add(spaceMachine);
			
			int spaceIndex;
			if((spaceIndex = spaceMachine.canTakeJob(job)) != -1) {
				spaceMachine.assignJobIntoFreeSpace(job, spaceIndex);
				
				for(Machine s : sortedMachines) {
					mQueueSpaces.add(s);
				}
				return;
			}
		}
		// No se pudo agregar el trabajo en espacios libres
		for(Machine s : sortedMachines) {
			mQueueSpaces.add(s);
		}
		
		
		// Agrego el trabajo en la maquina de menor tiempo total
		Machine chosenM = mQueueTimes.poll();
		assignment[jobIndex] = chosenM.num();
		chosenM.assignJobToEnd(jobs[jobIndex]);
		mQueueTimes.add(chosenM);
	}

	@Override
	protected int[] getAssignment() {
		return assignment;
	}


	private class MachineComparatorByTime implements Comparator<Machine>{
    
		@Override
	    public int compare(Machine x, Machine y){
			return x.time() - y.time();
		}
	}
	
	private class MachineComparatorBySpace implements Comparator<Machine>{
	    
		@Override
	    public int compare(Machine x, Machine y){
			return x.space() - y.space();
		}
	}
}
