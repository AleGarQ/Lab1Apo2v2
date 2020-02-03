package model;

import java.util.ArrayList;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.IsNotListedException;
import exceptions.NoTurnsForAttendException;
import exceptions.UserWithoutTurnException;

public class Controller {
	private ArrayList<User> users;
	private String lastTurn;
	private String actualTurn;
	private int turnsGiven;
	private int turnsAttended;
	
	public Controller(String turnToStart) {
		users = new ArrayList<User>();
		lastTurn = "A0/";
		actualTurn = turnToStart;
		turnsGiven = 0;
		turnsAttended = 0;
	}
	
	public void addUser(String doc, String id, String name, String surname, String phone, String adress) throws AlreadyOnListException, EmptyFieldException {
		if(doc != "" && id != "" && name != "" && surname != "") {
			if(searchUserToAdd(id) == null) {
				User aux = new User(doc, id, name, surname, phone, adress, null, false);
				users.add(aux);
			}else {
				throw new AlreadyOnListException(id);
			}
		}else {
			if(doc == "") {
				throw new EmptyFieldException("DOCUMENT TYPE");
			}else if(id == "") {
				throw new EmptyFieldException("DOCUMENT NUMBER");
			}else if(name == "") {
				throw new EmptyFieldException("NAME");
			}else if(surname == "") {
				throw new EmptyFieldException("SURNAME");
			}
		}
	}
	
	public User searchUserToAdd(String id) {
		User aux = null;
		
		for(int i = 0; i < users.size() && aux == null; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId())) {
				aux = users.get(i);
			}
		} 
		
		return aux;
	}
	
	public User searchUser(String id) throws IsNotListedException{
		User aux = null;
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId())) {
				aux = users.get(i);
				found = true;
			}
		}
		
		if(!found) {
			throw new IsNotListedException(id);
		}
		
		return aux;
	}
	
	public String getUserTurn(String id) throws IsNotListedException, UserWithoutTurnException{
		String info = null;
		String turn = null;
		
		if(searchUser(id) != null) {
			info = searchUser(id).toString();
			turn = searchUser(id).getTurn();
		}
		
		if(turn == null) {
			throw new UserWithoutTurnException(id);
		}
			
		return info + "		" + turn;
	}
	
	public String giveTurn(String id) throws IsNotListedException{
		char letter = lastTurn.charAt(0);
		char tens = lastTurn.charAt(1);
		char units = lastTurn.charAt(2);
		
		if(units == '9') {
			units = '0';
			if(tens == '9') {
				tens = '0';
				if(letter == 90) {
					letter = 65;
				}else {
					letter++;
				}
			}else {
				tens++;
			}
		}else {
			units++;
		}
		lastTurn = ""+letter+tens+units;
		turnsGiven++;
		
		if(searchUser(id) != null) {
			searchUser(id).setTurn(lastTurn);
		}
		
		return lastTurn;
	}
	
	public String nextTurn() throws NoTurnsForAttendException{
		if(turnsAttended < turnsGiven && turnsGiven != 0) {
			char letter = actualTurn.charAt(0);
			char tens = actualTurn.charAt(1);
			char units = actualTurn.charAt(2);
			
			if(units == '9') {
				units = '0';
				if(tens == '9') {
					tens = '0';
					if(letter == 90) {
						letter = 65;
					}else {
						letter++;
					}
				}else {
					tens++;
				}
			}else {
				units++;
			}
			actualTurn = ""+letter+tens+units;
			turnsAttended++;
		}else if(turnsGiven == 0){
			throw new NoTurnsForAttendException("It was not possible to give next turn because there are not turns given.");
		}else {
			throw new NoTurnsForAttendException("The actual turn was given for no user.");
		}
		
		return "The next turn is: "+actualTurn;
	}
	
	public String getActualTurn() {
		return "Actual turn is: "+actualTurn;
	}
	
	public void setIfWasServed(int served) {
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(actualTurn.equalsIgnoreCase(users.get(i).getTurn())) {
				if(served == 1) {
					users.get(i).setServed(true);
				}else {
					users.get(i).setServed(false);
				}
				found = true;
			}
		}
	}
	
	public void setTurnToUser(String id, String turn) throws IsNotListedException{
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId())) {
				users.get(i).setTurn(turn);
				found = true;
			}
		}
		
		if(!found) {
			throw new IsNotListedException(id);
		}
	}
}
