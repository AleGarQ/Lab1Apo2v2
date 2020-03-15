package model;

import java.io.Serializable;

public class Turn implements Serializable, Comparable<Turn>{
	private static final long serialVersionUID = 1L;
	private String code;
	private TurnType type;
	private String builtDate;
	private boolean userAway;
	private boolean served;

	public Turn(String code, TurnType type, boolean userAway, String builtDate, boolean served) {
		this.code = code;
		this.type = type;
		this.userAway = userAway;
		this.builtDate = builtDate;
		this.served = served;
	}

	public boolean isUserAway() {
		return userAway;
	}

	public void setUserAway(boolean userAway) {
		this.userAway = userAway;
	}
	
	public boolean isServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	public String getCode() {
		return code;
	}

	public TurnType getType() {
		return type;
	}
	
	public String getBuiltDate() {
		return builtDate;
	}
	
	public String toString() {
		return "["+code + ", " + getType().getName() + ", " + getType().getDuration() + "]";
	}

	@Override
	public int compareTo(Turn o) {
		int result;
		
		if(o.getType().getName().equalsIgnoreCase(type.getName())) {
			result = 0;
		}else {
			result = type.getName().compareTo(o.getType().getName());
		}
		
		return result;
	}
}