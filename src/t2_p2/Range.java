package t2_p2;

public class Range{
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
	
	public Range[] split(Range r){
		Range[] ranges = new Range[2];

		if (inRange(r)) {
			Range newLeftRange  = new Range(this.start(), r.start());
			Range newRigthRange = new Range(this.end(), r.end());
			
			ranges[0] = newLeftRange;
			ranges[1] = newRigthRange;
			
			return ranges;
		}
		ranges[0] = r;
		ranges[1] = new Range(0,0);
		return ranges;
	}
	
	public int length(){
		return end - start;
	}
	
	public boolean inRange(int num){
		return num >= start && num <= end;
	}
	
	public boolean inRange(Range r){
		return this.start < r.end || this.end < r.start;
	}
}
