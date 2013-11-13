package t2;

public class Plan {
	private final int[] assignment;
	private final int makespan;
	
	public Plan(int[] assignment, int makespan) {
		this.assignment = assignment;
		this.makespan = makespan;
	}

	public int[] getAssignment() {
		return assignment;
	}

	public int getMakespan() {
		return makespan;
	}
}
