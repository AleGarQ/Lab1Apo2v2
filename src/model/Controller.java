package model;

import java.util.ArrayList;

import exceptions.AlreadyOnListException;
import exceptions.IsNotListedException;

public class Controller {
	private ArrayList<User> users;
	private String lastTurn;
	private String actualTurn;
	
	public Controller() {
		users = new ArrayList<User>();
		lastTurn = "A0/";
		actualTurn = "A00";
	}
	
	public void addUser(String doc, String id, String name, String surname, String phone, String adress) throws AlreadyOnListException {
		if(searchUser(id) != null) {
			User aux = new User(doc, id, name, surname, phone, adress, null);
			users.add(aux);
		}else {
			throw new AlreadyOnListException(id);
		}
	}
	
	public User searchUser(String id) {
		User aux = null;
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId())) {
				aux = users.get(i);
				found = true;
			}
		}
		
		return aux;
	}
	
	public String getUserTurn(String id) throws IsNotListedException{
		String turn = null;
		boolean found = false;
		
		for(int i = 0; i < users.size() && !found; i++) {
			if(id.equalsIgnoreCase(users.get(i).getId())) {
				turn = users.get(i).getTurn();
				found = true;
			}
		}
		
		if(!found) {
			throw new IsNotListedException(id);
		}
		
		return turn;
	}
	
	public String giveTurn() {
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
		
		return lastTurn;
	}
}
