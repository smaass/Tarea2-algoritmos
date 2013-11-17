package t2_p2;

import java.util.ArrayList;

public class Machine {
	private int machineNum;
	private ArrayList<Range> freeTimes;
	private int freeRanges;
	
	public Machine(int num) {
		machineNum = num;
		freeTimes =  new ArrayList<Range>();
		freeRanges = 1;
		freeTimes.add(new Range(0, Integer.MAX_VALUE));
		
		//System.out.println("init");
		//printM(freeTimes);
	}
	
	public void assignJob(Job j){
		//System.out.println("Job: " + ++jobs);

		int timeInMachine = j.time(machineNum);
		ArrayList<Range> jobBusyTimes = j.busyTimes();
		ArrayList<Range> tempFreeTimes = new ArrayList<Range>();// = (ArrayList<Range>) freeTimes.clone();
		
		//System.out.println("free antes:");
		//printM(freeTimes);
		
		// A todos los rangos disponibles de la maquina, se sustrae los rangos de tiempo ocupados por el trabajo
		tempFreeTimes = (ArrayList<Range>) freeTimes.clone();
		int size = tempFreeTimes.size();
		int i;
		Range r;
		for (i = 0; i < size; i++){
			r = tempFreeTimes.get(i);

			for(Range jRange: jobBusyTimes) {
				if (r.inRange(jRange)) {
					Range[] splits = r.split(jRange);

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
		Range selectedRange = null;
		for (i = 0; i < size; i++) {
			r = tempFreeTimes.get(i);

			if(r.length() >= timeInMachine) {
				// Este es el rango donde se insertara el job!
				selectedRange = new Range(r.start(), r.start() + timeInMachine);
				j.addBusyTime(selectedRange); // el trabajo tiene ahora esta zona de tiempo ocupada
				break; // me salgo del ciclo
			}
		}
		
		//if(selectedRange != null)
			//System.out.println("selected: " + selectedRange.start() + " " + selectedRange.end());
		
		// Se inserta el rango de tiempo dentro de algun rango disponible real de la maquina y se actualiza
		for (i = 0; i < size; i++) {
			r = tempFreeTimes.get(i);

			if (r.inRange(selectedRange)) {
				Range[] splits = r.split(selectedRange);
				
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
		Range r = freeTimes.get(0);
		if (r != null){
			return r.length();
		} else {
			return 0; 
		}
	}
	
	public void printM(ArrayList<Range> rl){
		for(Range r: rl){
			System.out.println("start:" + r.start() + " end: " + r.end());
		}
	}
	
	// Ultimo tiempo ocupado por la maquina
	public int time() {
		if (freeRanges == 0){
			return 0;
		} else {
			return freeTimes.get(freeRanges-1).start();
		}
	}
	
	public void clear() {
		freeTimes =  new ArrayList<Range>();
		freeTimes.add(new Range(0, Integer.MAX_VALUE));
		freeRanges = 1;
	}
	
	public int num() {
		return  machineNum;
	}
}
