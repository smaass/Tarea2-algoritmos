package t2_p2;

public class Range implements Comparable<Range>{
	private int start;
	private int end;
	
	public Range(int start, int end){
		assert(start <= end);
		this.start = start;
		this.end   = end;
	}
	
	public int start(){
		return start;
	}
	public int end(){
		return end;
	}
	
	public boolean inRange(int num){
		return num >= start && num <= end;
	}
	
	public boolean inRange(Range r){
		return this.start < r.end || this.end < r.start;
	}
	
	@Override
	public int compareTo(Range r){
		return this.start - r.start;
	}
}
