package exceptions;

public class IsNotListedException extends Exception {
	String id;

	public IsNotListedException(String id) {
		super("The user with id: " + id + " is not listed\n");
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
