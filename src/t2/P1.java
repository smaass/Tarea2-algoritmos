package t2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P1 {
	public static final String experimentsFolder = "results/";
	
	public static void main(String[] args) {
		/*
		List<Job> jobs = randomJobsList(10);
		System.out.println("Jobs List: " + jobsToString(jobs) + "\n");
		Planning online = new OnlinePlanning(4, jobs);
		Planning offline = new OfflinePlanning(4, jobs);
		System.out.println("Online plan: " + intArrayToString(online.getAssignment()));
		System.out.println("Online makespan: " + online.makespan() + "\n");
		System.out.println("Offline plan: " + intArrayToString(offline.getAssignment()));
		System.out.println("Offline makespan: " + offline.makespan());*/
		System.out.println("Experimentando...");
		experiments();
		System.out.println("Fin :D");
	}
	
	public static void experiments() {
		for (int i=8; i<=16; i+=2){
			experiment(4, i, 400);
		}
	}
	
	public static void experiment(int machines, int jobsNum, int runs) {
		try {
			String filename = experimentsFolder + "E" + machines + "M" + jobsNum + "J" + ".mat";
			BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
			int[] line = new int[2];
			for (int i=0; i<runs; i++) {
				List<Job> jobs = randomJobsList(jobsNum);
				comparePlans(machines, jobs, line);
				writer.write(line[0] + " " + line[1] + "\n");
			}
			writer.close();
			System.out.println("Listo " + filename + "!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void comparePlans(int machines, List<Job> jobs, int[] output) {
		Planning offline = new OfflinePlanning(machines, jobs);
		Planning online = new OnlinePlanning(machines, jobs);
		output[0] = offline.makespan();
		output[1] = online.makespan();
	}
	
	public static String jobsToString(List<Job> jobs) {
		String s = "";
		for (Job j : jobs) {
			s += j.time() + " ";
		}
		return s;
	}
	
	public static String intArrayToString(int[] array) {
		String s = "[";
		for (int i=0; i<array.length; i++) {
			s += array[i];
			if (i < array.length-1)
				s += ", ";
		}
		s += "]";
		return s;
	}
	
	public static List<Job> randomJobsList(int size) {
		List<Job> jobs = new ArrayList<Job>();
		for (int i=0; i<size; i++) {
			jobs.add(Job.randomJob());
		}
		return jobs;
	}
	
	private static class Machine {
		private int busyTime;
		
		public Machine() {
			busyTime = 0;
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
	}
	
	private static abstract class Planning {
		protected Machine[] machines;
		protected Job[] jobs;
		
		public Planning(int numMachines, List<Job> jobs) {
			machines = new Machine[numMachines];
			for (int i=0; i<numMachines; i++) {
				machines[i] = new Machine();
			}
			this.jobs = new Job[jobs.size()];
			jobs.toArray(this.jobs);
			plan();
		}
		
		public int makespan() {
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
		protected abstract int[] getAssignment();
	}
	
	private static class OnlinePlanning extends Planning {
		private int[] assignment;
		
		public OnlinePlanning(int numMachines, List<Job> jobs) {
			super(numMachines, jobs);
		}

		protected void plan() {
			assignment = new int[jobs.length];
			for (int i=0; i<jobs.length; i++) {
				assignToLessBusyMachine(i);
			}
		}
		
		private void assignToLessBusyMachine(int jobIndex) {
			Machine chosenMachine = machines[0];
			int minTime = chosenMachine.time();
			int chosenIndex = 0;
			for (int i=1; i<machines.length; i++) {
				if (minTime > machines[i].time()) {
					chosenMachine = machines[i];
					minTime = chosenMachine.time();
					chosenIndex = i;
				}
			}			
			assignment[jobIndex] = chosenIndex;
			chosenMachine.assignJob(jobs[jobIndex]);
		}

		@Override
		protected int[] getAssignment() {
			return assignment;
		}
	}
	
	private static class OfflinePlanning extends Planning {
		private int[] assignment;

		public OfflinePlanning(int numMachines, List<Job> jobs) {
			super(numMachines, jobs);
		}
		
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
}
