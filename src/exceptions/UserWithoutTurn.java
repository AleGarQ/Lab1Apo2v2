package exceptions;

public class UserWithoutTurn extends Exception {
	private String id;
	
	public UserWithoutTurn(String id) {
		super("The user with id: "+id+" does not have a turn\n");
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
}
