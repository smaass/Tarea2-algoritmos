package t2_p2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;

public class Machine {
	private int machineNum;
	private ArrayList<Range> freeTimes;
	private Range endRange;
	private int maxBusyTime;
	
	public Machine(int num) {
		machineNum = num;
		maxBusyTime = 0;
		freeTimes =  new ArrayList<Range>();
		endRange  =  new Range(0, Integer.MAX_VALUE);
	}
	
	public void assignJob(Job j) {
		int freeSpace = canTakeJob(j);
		if (freeSpace > -1) {
			assignJobIntoFreeSpace(j, freeSpace);
		}
		else {
			assignJobToEnd(j);
		}
	}
	
	private void createFreeTimes(ArrayList<Range> jobBusyTimes){
		for (Range r: jobBusyTimes){
			Range[] splits = endRange.split(r);
			
			if(splits[0]!= null){
				freeTimes.add(splits[0]);
				endRange = splits[1];
				maxBusyTime = endRange.start();
			}
		}
	}
	
	public void assignJobToEnd(Job j) {
		int timeInMachine = j.time(machineNum);
		int newMaxBusyTime = timeInMachine + maxBusyTime;
		
		createFreeTimes(j.busyTimes());

		// Se agrega el job como ultimo proceso de la maquina y se marca un nuevo rango ocupado
		Range jobBusyTime = new Range(maxBusyTime, newMaxBusyTime);
		j.addBusyTime(jobBusyTime);

		maxBusyTime = newMaxBusyTime;
		endRange.setStart(maxBusyTime);
	}
	
	public void assignJobIntoFreeSpace(Job j, int i) {
		int timeInMachine = j.time(machineNum);

		Range[] freeTimesArray = (Range[]) freeTimes.toArray();

		Range selectedFreeRange = freeTimesArray[i];

		Range[] splits = selectedFreeRange.split(new Range(selectedFreeRange.start(), selectedFreeRange.start() + timeInMachine));

		freeTimes.remove(selectedFreeRange);
		
		// Este rango de tiempo esta ahora ocupado por el trabajo
		j.addBusyTime(splits[0]);
		
		if(splits[1].length() > 0){
			freeTimes.add(splits[1]);
		}

		Collections.sort(freeTimes, new RangeComparatorByStart());
	}
	
	public int canTakeJob(Job j) {
		Range free;
		
		for(int i = 0; i < freeTimes.size(); i++){
			free = freeTimes.get(i);
			if(j.fitInRange(machineNum, free)){
				return i;
			}
		}
		return -1;
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
	
	// Ultimo tiempo ocupado por la maquina
	public int time() {
		return maxBusyTime;
	}
	
	public void clear() {
		maxBusyTime = 0;
		freeTimes =  new ArrayList<Range>();
	}
	
	public int num() {
		return  machineNum;
	}
	
	private class RangeComparatorByStart implements Comparator<Range>{
		
		@Override
		public int compare(Range x, Range y){
			return x.start() - y.start();
		}
	}
}
