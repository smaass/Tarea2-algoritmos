package t2_p2;

public class Range{
	private int start;
	private int end;
	private boolean last;
	
	public Range(int start, int end, boolean last){
		assert(start <= end);
		this.start = start;
		this.end   = end;
		this.last = last;
	}
	
	public Range(int start, int end){
		this.last = false;
	}
	
	public boolean last(){
		return this.last;
	}
	
	public int start(){
		return start;
	}
	
	public void setStart(int s){
		this.start = s;
	}
	
	public int end(){
		return end;
	}
	
	public void setEnd(int s){
		this.end = s;
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
		ranges[0] = null;
		ranges[1] = this;
		return ranges;
	}
	
	public void substract(Range r){
		if (r.start() < this.end() && r.start() > this.start()){
			this.setEnd(r.start());
		} else if(r.end() > this.start() && r.end() < this.end()){
			this.setStart(r.end());
		}
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
