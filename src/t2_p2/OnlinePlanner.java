package t2_p2;

import java.util.List;
import java.util.PriorityQueue;

import t2_p2.Machine.SpaceInfo;

public class OnlinePlanner extends PlanBuilder {
	
	public OnlinePlanner(int numMachines, List<Job> jobs) {
		super(numMachines, jobs);
	}

	@Override
	protected void plan() {
		for (Job j : jobs) {
			addJob(j);
		}
	}
	
	private void addJob(Job j) {
		boolean[] added = addJobToSpaces(j);
		PriorityQueue<Machine> mQueue = new PriorityQueue<Machine>();
		// Agregamos a la cola las maquinas a las que no se les asigno el trabajo en ningun espacio
		for (int i=0; i<added.length; i++) {
			if (!added[i]) mQueue.add(machines[i]);
		}
		// Agregamos el trabajo a las maquinas segun orden de carga
		while (!mQueue.isEmpty()) {
			mQueue.poll().putJobAtEnd(j);
		}
	}
	
	private boolean[] addJobToSpaces(Job j) {
		boolean[] added = new boolean[machines.length];
		int availableSpaces = machines.length;
		SpaceInfo[] spaces = new SpaceInfo[machines.length];
		
		// Asigna las etapas a los espacios vacios, en orden creciente segun el comienzo
		// del espacio
		while (availableSpaces > 0) {
			availableSpaces = 0;
			int firstCandidate = -1;
			
			// Busca el primer espacio de tamano suficiente en cada maquina
			for (int i=0; i<machines.length; i++) {
				if (!added[i]) {
					spaces[i] = machines[i].findSpace(j);
					if (spaces[i] != null) {
						availableSpaces++;
						if (firstCandidate == -1) firstCandidate = i;
					}
				}
			}
			
			if (availableSpaces == 0) break;
			
			// Busca el espacio que comienza antes
			int candidate = firstCandidate;
			for (int i=1; i<machines.length; i++) {
				if (!added[i] && spaces[i] != null && spaces[candidate].start > spaces[i].start) {
					candidate = i;
				}
			}
			
			// Agrega el trabajo al espacio encontrado
			machines[candidate].putJobInSpace(spaces[candidate], j);
			added[candidate] = true;
		}
		
		return added;
	}

	@Override
	protected int[][] getAssignment() {
		return null;
	}
}
