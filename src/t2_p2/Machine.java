package t2_p2;

import java.util.ArrayList;

public class Machine implements Comparable<Machine>{
	private int machineNum;
	private ArrayList<int[]> freeTimes;
	private int freeRanges;
	
	public Machine(int num) {
		machineNum = num;
		freeTimes =  new ArrayList<int[]>();
		freeRanges = 1;
		freeTimes.add(Range.newRange(0, Integer.MAX_VALUE));
		
		//System.out.println("init");
		//printM(freeTimes);
	}
	
	public void assignJob(Job j){
		//System.out.println("Job: " + ++jobs);

		int timeInMachine = j.time(machineNum);
		ArrayList<int[]> jobBusyTimes = j.busyTimes();
		ArrayList<int[]> tempFreeTimes = new ArrayList<int[]>();// = (ArrayList<Range>) freeTimes.clone();
		
		//System.out.println("free antes:");
		//printM(freeTimes);
		
		// A todos los rangos disponibles de la maquina, se sustrae los rangos de tiempo ocupados por el trabajo
		tempFreeTimes = (ArrayList<int[]>) freeTimes.clone();
		int size = tempFreeTimes.size();
		int i;
		int[] r;
		for (i = 0; i < size; i++){
			r = tempFreeTimes.get(i);

			for(int[] jRange: jobBusyTimes) {
				if (Range.inRange(r, jRange)) {
					int[][] splits = Range.split(r, jRange);

					//System.out.println("split");
					//System.out.println("oRange:[" + r.start() + "," + r.end()+"] jRange:["+jRange.start()+","+jRange.end()+"]");
					
					if (splits[1] != null) {
						r = tempFreeTimes.set(i,splits[1]);
					}
					if (splits[0] != null) {
						tempFreeTimes.add(i,splits[0]);
						size++;
					}
				}
			}
		}
		
		//System.out.println("temporal despues:");
		//printM(tempFreeTimes);
		
		// Ahora, se recorren los rangos de tiempo disponibles, y se determina donde se puede agregar
		int[] selectedRange = new int[2];
		for (i = 0; i < size; i++) {
			r = tempFreeTimes.get(i);

			if(Range.length(r) >= timeInMachine) {
				// Este es el rango donde se insertara el job!
				selectedRange = Range.newRange(Range.start(r), Range.start(r) + timeInMachine);
				j.addBusyTime(selectedRange); // el trabajo tiene ahora esta zona de tiempo ocupada
				break; // me salgo del ciclo
			}
		}
		
		//if(selectedRange != null)
			//System.out.println("selected: " + selectedRange.start() + " " + selectedRange.end());
		
		// Se inserta el rango de tiempo dentro de algun rango disponible real de la maquina y se actualiza
		for (i = 0; i < size; i++) {
			r = tempFreeTimes.get(i);

			if (Range.inRange(r, selectedRange)) {
				int[][] splits = Range.split(r, selectedRange);
				
				if (splits[1] != null) {
					r = freeTimes.set(i, splits[1]);
				}
				if (splits[0] != null) {
					freeTimes.add(i, splits[0]);
					freeRanges++;
					size++;
				}
				break;
			}
		}
		//System.out.println("free despues:");
		//printM(freeTimes);
	}
	
	// Espacio libre de la maquina
	public int space(){
		int[] r = freeTimes.get(0);
		if (r != null){
			return Range.length(r);
		} else {
			return 0; 
		}
	}
	
	public void printM(ArrayList<int[]> rl){
		for(int[] r: rl){
			System.out.println("start:" + Range.start(r) + " end: " + Range.end(r));
		}
	}
	
	// Ultimo tiempo ocupado por la maquina
	public int time() {
		if (freeRanges == 0){
			return 0;
		} else {
			return Range.start(freeTimes.get(freeRanges-1));
		}
	}
	
	public int ranges(){
		return freeRanges;
	}
	
	public void clear() {
		freeTimes.clear();
		//freeTimes =  new ArrayList<Range>();
		freeTimes.add(Range.newRange(0, Integer.MAX_VALUE));
		freeRanges = 1;
	}
	
	public int num() {
		return  machineNum;
	}
	
	public int getTime(int i){
		return Range.start(freeTimes.get(i));
	}

	@Override
	public int compareTo(Machine m) {
		if(this.ranges() > 0 && m.ranges() > 0){
			return this.getTime(0) - m.getTime(0);
		}
		return 0;
	}
}
