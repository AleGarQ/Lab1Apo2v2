package model;

import java.util.Comparator;

public class TurnDurationComparator implements Comparator<Turn> {

	@Override
	public int compare(Turn o1, Turn o2) {
		int comparation;
		double duration1 = o1.getType().getDuration();
		double duration2 = o2.getType().getDuration();
		
		if(duration1 < duration2) {
			comparation = -1;
		}else if(duration1 > duration2) {
			comparation = 1;
		}else {
			comparation = 0;
		}
		
		return comparation;
	}

}
