package exceptions;

public class EmptyFieldException extends Exception {
	private String field;
	
	public EmptyFieldException(String field) {
		super("The field: "+field+" is empty\n");
		this.field = field;
	}
	
	public String getField() {
		return field;
	}
}
