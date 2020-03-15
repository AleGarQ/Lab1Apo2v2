package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import exceptions.InvalidDateException;

public class Dte {
	private SimpleDateFormat dateFormat;
	private String systemDate;
	private String attendedDate;
	private boolean changed;
	
	public Dte() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		systemDate = dateFormat.format(new Date());
		attendedDate = systemDate;
		changed = false;
	}
	
	@SuppressWarnings("deprecation")
	public String getSystemDate() throws ParseException {
		Date actual = new Date();
		String[] space = systemDate.split(" ");
		String[] date = space[0].split("/");
		String[] time = space[1].split(":");
		
//		22/01/2021 15:30:00
		if(changed) {
			actual.setDate(actual.getDate() + Integer.parseInt(date[0]));
			actual.setMonth(actual.getMonth() + Integer.parseInt(date[1]));
			actual.setYear(actual.getYear() + Integer.parseInt(date[2]));
			actual.setHours(actual.getHours() + Integer.parseInt(time[0]));
			actual.setMinutes(actual.getMinutes() + Integer.parseInt(time[1]));
			actual.setSeconds(actual.getSeconds() + Integer.parseInt(time[2]));
		}
		
		return dateFormat.format(actual);
	}
	
	@SuppressWarnings("deprecation")
	public void setSystemDate(String update) throws ParseException, InvalidDateException {
		if(update.compareTo(getSystemDate()) > 0) {
			Date comprobator = dateFormat.parse(update);
			Date actual = new Date();
			
			systemDate = (comprobator.getDate() - actual.getDate()) + "/" + (comprobator.getMonth() - actual.getMonth()) + "/" + (comprobator.getYear() - actual.getYear()) + 
						" " + (comprobator.getHours() - actual.getHours()) + ":" + (comprobator.getMinutes() - actual.getMinutes()) + ":" + (comprobator.getSeconds() - actual.getSeconds());
			
			changed = true;
		}else {
			throw new InvalidDateException(update);
		}
	}
	
	@SuppressWarnings("deprecation")
	public String advanceTime(int minutes, int seconds) throws ParseException {
		Date rest = dateFormat.parse(attendedDate);
		rest.setSeconds(rest.getSeconds() + seconds);
		rest.setMinutes(rest.getMinutes() + minutes);
		
		if(dateFormat.format(rest).compareTo(getSystemDate()) < 0) {
			attendedDate = dateFormat.format(rest);
		}
		
		return dateFormat.format(rest);
	}
}
