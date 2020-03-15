package exceptions;

public class InvalidDateException extends Exception {
	private String date;
	
	public InvalidDateException(String date) {
		super("The date can't be updated with \""+ date +"\" because is a past date");
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
}
