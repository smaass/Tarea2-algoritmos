package t2_p2;

import java.util.ArrayList;
import java.util.List;

public class Utils {
	
	public static ArrayList<String> permute(String s) {
		ArrayList<String> lst = new ArrayList<String>();
		permuteRec("", s, lst);
		return lst;
	}
	
	private static void permuteRec(String beginningString, String endingString, ArrayList<String> lst) {
		if (endingString.length() <= 1) {
			lst.add(beginningString + endingString);
		}
		else {
			for (int i = 0; i < endingString.length(); i++) {
				String newString = endingString.substring(0, i) + endingString.substring(i + 1);
				permuteRec(beginningString + endingString.charAt(i), newString, lst);
			}
		}
	}
	
	/**
	 * Returns a bi-dimensional array where each row represents a permutation
	 * of the sequence [0, 1, ..., m-1]. Each permutation is represented as an array
	 * of integers.
	 * 
	 * @param m the number of integers to permute
	 * @return the permutation array
	 */
	public static int[][] permutationsArray(int m) {
		String s = "";
		for (int i=0; i<m; i++) s += i;
		List<String> lst = permute(s);
		
		int[][] permutations = new int[lst.size()][m];
		for (int i=0; i<lst.size(); i++) {
			s = lst.get(i);
			for (int j=0; j<m; j++) {
				permutations[i][j] = Character.getNumericValue(s.charAt(j));
			}
		}
		
		return permutations;
	}
}
