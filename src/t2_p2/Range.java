package t2_p2;

public class Range{
	private int start;
	private int end;
	private boolean last;
	
	public Range(int start, int end){
		assert(start <= end);
		this.start = start;
		this.end   = end;
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
		if(r.start() > this.start() && r.start() < this.end() && r.end() > this.start() && r.end() < this.end()){ // r completamente contenido en this
			// genera dos resultados del split no vacios;
			ranges[0]  = new Range(this.start(), r.start());
			ranges[0]  = new Range(r.end(), this.end());
		} else if(r.start() < this.end() && r.start() > this.start()){ // r esta a la derecha
			// se genera un split a la izquierda solamente
			ranges[0]  = new Range(this.start(), r.start());
			ranges[1] = null;

		} else if(r.end() > this.start() && r.end() < this.end()){ // r esta a la izquierda
			// se genera un split a la derecha solamente
			ranges[0]  = null;
			ranges[1] = new Range(r.end(), this.end());
		}
		else{
			// no hay splits
			ranges[0] = null;
			ranges[1] = null;
		}
		return ranges;
	}
	
	public Range substract(Range r){
		if (r.start() < this.end() && r.start() > this.start()){
			this.setEnd(r.start());
		} else if(r.end() > this.start() && r.end() < this.end()){
			this.setStart(r.end());
		}
		return this;
	}
	
	public int length(){
		return end - start;
	}
	
	public boolean inRange(int num){
		return num >= start && num <= end;
	}
	
	public boolean inRange(Range r){
		return r.start() < this.end() && r.start() > this.start() || r.end() > this.start() && r.end() < this.end();
	}
}
