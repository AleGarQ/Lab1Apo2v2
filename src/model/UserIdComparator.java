package model;

import java.util.Comparator;

public class UserIdComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		int comparation;
		String id1 = o1.getId();
		String id2 = o2.getId();
		
		if(id1.compareTo(id2) < 0) {
			comparation = -1;
		}else if(id1.compareTo(id2) > 0) {
			comparation = 1;
		}else {
			comparation = 0;
		}
		
		return comparation;
	}

}
