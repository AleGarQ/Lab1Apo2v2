package model;

import java.io.Serializable;

public class TurnType implements Serializable{
	private static final long serialVersionUID = 1L;
	private String name;
	private double duration;
	
	public TurnType(String name, double duration) {
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}
	
}
