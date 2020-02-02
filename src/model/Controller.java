package model;

import java.util.ArrayList;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.IsNotListedException;
import exceptions.UserWithoutTurn;

public class Controller {
	private ArrayList<User> users;
	private String lastTurn;
	private String actualTurn;
	
	public Controller() {
		users = new ArrayList<User>();
		lastTurn = "A0/";
		actualTurn = "A00";
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
	
	public String getUserTurn(String id) throws IsNotListedException, UserWithoutTurn{
		String info = null;
		String turn = null;
		
		if(searchUser(id) != null) {
			info = searchUser(id).toString();
			turn = searchUser(id).getTurn();
		}
		
		if(turn == null) {
			throw new UserWithoutTurn(id);
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
		
		if(searchUser(id) != null) {
			searchUser(id).setTurn(lastTurn);
		}
		
		return lastTurn;
	}
	
	public String nextTurn() {
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
		
		return actualTurn;
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
	
	public void setIfWasServed(String turn, int selection) {
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(turn.equalsIgnoreCase(users.get(i).getTurn())) {
				if (selection == 1) {
					users.get(i).setServed(true);
				}else {
					users.get(i).setServed(false);
				}
			}
		}
	}
}
