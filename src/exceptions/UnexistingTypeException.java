package exceptions;

public class UnexistingTypeException extends Exception {
	private String type;
	
	public UnexistingTypeException(String type) {
		super("The entered type \""+ type + "\" was not found");
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
