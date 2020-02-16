package exceptions;

public class TurnIsNotAttendedExcpetion extends Exception {
	String turn;
	
	public TurnIsNotAttendedExcpetion(String turn) {
		super("The user have the turn: "+turn+" and this one has not attended");
		this.turn = turn;
	}

	public String getTurn() {
		return turn;
	}
}
