package t2_p2;

public class Range{
	public static int[] newRange(int start, int end){
		assert(start <= end);
		int[] ranges = new int[2];
		ranges[0] = start;
		ranges[1] = end;
		return ranges;
	}

	public static int start(int[] range){
		return range[0];
	}

	public static int[] setStart(int[] range, int s){
		range[0] = s;
		return range;
	}

	public static int end(int[] range){
		return range[1];
	}

	public static int[] setEnd(int[] range, int e){
		range[1] = e;
		return range;
	}

	public static int[][] split(int[] tr, int[] r){
		int[][] ranges = new int[2][2];
		
		if(Range.start(r) > Range.start(tr) && Range.start(r) < Range.end(tr) && Range.end(r) > Range.start(tr) && Range.end(r) < Range.end(tr)){ // r completamente contenido en this
			// genera dos resultados del split no vacios;
			ranges[0]  = Range.newRange(Range.start(tr), Range.start(r));
			ranges[0]  = Range.newRange(Range.end(r), Range.end(tr));
		} else if(Range.start(r) < Range.end(tr) && Range.start(r) > Range.start(tr)){ // r esta a la derecha
			// se genera un split a la izquierda solamente
			ranges[0]  = Range.newRange(Range.start(tr), Range.start(r));
			ranges[1] = null;

		} else if(Range.end(r) > Range.start(tr) && Range.end(r) < Range.end(tr)){ // r esta a la izquierda
			// se genera un split a la derecha solamente
			ranges[0]  = null;
			ranges[1] = Range.newRange(Range.end(r), Range.end(tr));
		}
		else{
			// no hay splits
			ranges[0] = null;
			ranges[1] = null;
		}
		return ranges;
	}

	public static int[] substract(int[] tr, int[] r){
		if (Range.start(r) < Range.end(tr) && Range.start(r) > Range.start(tr)){
			Range.setEnd(tr,Range.start(r));
		} else if(Range.end(r) > Range.start(tr) && Range.end(r) < Range.end(tr)){
			Range.setStart(tr,Range.end(r));
		}
		return tr;
	}

	public static int length(int[] range){
		return Range.end(range) - Range.start(range);
	}

	public static boolean inRange(int[] range, int num){
		return num >= Range.start(range) && num <= Range.end(range);
	}

	public static boolean inRange(int[] tr, int[] r){
		return Range.start(r) < Range.end(tr) && Range.start(r) > Range.start(tr) || Range.end(r) > Range.start(tr) && Range.end(r) < Range.end(tr);
	}
}
