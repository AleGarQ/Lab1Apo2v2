package exceptions;

public class InvalidSelectionException extends Exception {
	private double selection;
	
	public InvalidSelectionException(double selection) {
		super("Your selection: "+selection+". Is not valid");
		this.selection = selection;
	}
	
	public double getSelection() {
		return selection;
	}
}
