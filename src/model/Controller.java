package model;

import java.util.ArrayList;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.IsNotListedException;
import exceptions.NoTurnsForAttendException;
import exceptions.TurnIsNotAttendedExcpetion;

public class Controller {
	private ArrayList<User> users;
	private Turn lastTurn;
	private Turn actualTurn;
	private int turnsGiven;
	private int turnsAttended;
	
	public Controller(String turnToStart, String firstTurn) {
		users = new ArrayList<User>();
		lastTurn = new Turn(firstTurn);
		actualTurn = new Turn(turnToStart);
		turnsGiven = 0;
		turnsAttended = 0;
	}
	
	public void addUser(String doc, String id, String name, String surname, String phone, String adress) throws AlreadyOnListException, EmptyFieldException {
		if(doc != "" && id != "" && name != "" && surname != "") {
			if(searchUserToAdd(id, doc) == null) {
				User aux = new User(doc, id, name, surname, phone, adress, false);
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
	
	public User searchUserToAdd(String id, String doc) {
		User aux = null;
		
		for(int i = 0; i < users.size() && aux == null; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId()) && doc.equalsIgnoreCase(users.get(i).getDocType())) {
				aux = users.get(i);
			}
		} 
		
		return aux;
	}
	
	public User searchUser(String id, String doc) throws IsNotListedException{
		User aux = null;
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId()) && doc.equalsIgnoreCase(users.get(i).getDocType())) {
				aux = users.get(i);
				found = true;
			}
		}
		
		if(!found) {
			throw new IsNotListedException(id);
		}
		
		return aux;
	}
	
	public String giveTurn(String id, String doc) throws IsNotListedException, TurnIsNotAttendedExcpetion{
		char letter = lastTurn.getCode().charAt(0);
		char tens = lastTurn.getCode().charAt(1);
		char units = lastTurn.getCode().charAt(2);
		
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
		
		if(searchUser(id, doc) != null && (searchUser(id, doc).getTurn() == null || searchUser(id, doc).wasServed())) {
			lastTurn = new Turn(""+letter+tens+units);
			turnsGiven++;
			searchUser(id, doc).setTurn(lastTurn);
		}else if(searchUser(id, doc).getTurn() != null && searchUser(id, doc).wasServed() == false){
			throw new TurnIsNotAttendedExcpetion(searchUser(id, doc).getTurn());
		}else {
			throw new IsNotListedException(id);
		}
		
		return lastTurn.getCode();
	}
	
	public String nextTurn() throws NoTurnsForAttendException{
		if(turnsAttended < turnsGiven && turnsGiven != 0) {
			char letter = actualTurn.getCode().charAt(0);
			char tens = actualTurn.getCode().charAt(1);
			char units = actualTurn.getCode().charAt(2);
			
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
			actualTurn = new Turn(""+letter+tens+units);
			turnsAttended++;
		}else if(turnsGiven == 0){
			throw new NoTurnsForAttendException("It was not possible to give next turn because there are not turns given.");
		}else {
			throw new NoTurnsForAttendException("The actual turn was given for no user.");
		}
		
		return "The next turn is: "+actualTurn.getCode();
	}
	
	public String getActualTurn() {
		return "Actual turn is: "+actualTurn.getCode();
	}
	
	public void setIfWasServed(int served) {
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(actualTurn.getCode().equalsIgnoreCase(users.get(i).getTurn())) {
				if(served == 1) {
					users.get(i).setServed(true);
				}else {
					users.get(i).setServed(false);
				}
				found = true;
			}
		}
	}
	
}
