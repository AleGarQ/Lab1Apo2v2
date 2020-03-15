package model;

import java.util.Comparator;

public class TurnCodeComparator implements Comparator<Turn> {

	@Override
	public int compare(Turn o1, Turn o2) {
		int comparation;
		String code1 = o1.getCode();
		String code2 = o2.getCode();
		
		if(code1.compareTo(code2) < 0) {
			comparation = -1;
		}else if(code1.compareTo(code2) > 0) {
			comparation = 1;
		}else {
			comparation = 0;
		}
		
		return comparation;
	}

}
