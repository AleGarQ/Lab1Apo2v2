package exceptions;

public class AlreadyOnListException extends Exception {
	String id;

	public AlreadyOnListException(String id) {
		super("The user with id: " + id + " is already on the system\n");
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
