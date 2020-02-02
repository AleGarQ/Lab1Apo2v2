package exceptions;

public class InvalidSelectionException extends Exception {
	private int selection;
	
	public InvalidSelectionException(int selection) {
		super("Your selection: "+selection+". Is not valid");
		this.selection = selection;
	}
	
	public int getSelection() {
		return selection;
	}
}
