package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.EmptyTurnsException;
import exceptions.InvalidDateException;
import exceptions.InvalidSelectionException;
import exceptions.IsNotListedException;
import exceptions.NoTurnsForAttendException;
import exceptions.TurnIsNotAttendedExcpetion;
import exceptions.UnexistingTypeException;

public class Controller {
	private ArrayList<User> users;
	private int turnsGiven;
	private int turnsAttended;
	private Dte dates;
	private TurnManager turns;
	
	public Controller(String turnToStart, String firstTurn) {
		users = new ArrayList<User>();
		dates = new Dte();
		turnsGiven = 0;
		turnsAttended = 0;
		turns = new TurnManager();
	}
	
	public void addUser(String doc, String id, String name, String surname, String phone, String adress) throws AlreadyOnListException, EmptyFieldException {
		if(doc != "" && id != "" && name != "" && surname != "") {
			if(searchUserToAdd(id, doc) == null) {
				User aux = new User(doc, id, name, surname, phone, adress);
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
	
	public String giveTurn(String id, String doc, String tType) throws IsNotListedException, TurnIsNotAttendedExcpetion, ParseException, UnexistingTypeException{
		String lastTurn = "";
		
		if(searchUser(id, doc) != null && (searchUser(id, doc).getTurn() == null || searchUser(id, doc).wasServed()) && turns.searchTurnType(tType.toUpperCase()) != null) {
			Turn turnToGive = turns.giveTurn(tType);
			lastTurn = turnToGive.getCode();
			turnsGiven++;
			searchUser(id, doc).setTurn(turnToGive);
		}else if(searchUser(id, doc).getTurn() != null && searchUser(id, doc).wasServed() == false){
			throw new TurnIsNotAttendedExcpetion(searchUser(id, doc).getTurn().getCode());
		}else if (searchUser(id, doc) == null){
			throw new IsNotListedException(id);
		}else {
			throw new UnexistingTypeException(tType);
		}
		
		return lastTurn;
	}
	
	public String nextTurn() throws NoTurnsForAttendException, EmptyTurnsException{
		String actualTurn = null;
		
		if(turnsAttended < turnsGiven && turnsGiven != 0) {
			try {
				actualTurn = turns.nextTurn().getCode();
				turnsAttended++;
			}catch (NoTurnsForAttendException e) {
				return e.getMessage();
			}
		}else if(turnsGiven == 0){
			throw new NoTurnsForAttendException("It was not possible to give next turn because there are not turns given.");
		}else {
			throw new NoTurnsForAttendException("The actual turn was given for no user.");
		}
		
		return "The next turn is: \""+actualTurn+"\" and it was not given to any user.";
	}
	
	public String getActualTurn() throws EmptyTurnsException {
		String msg = "Next turn to attend is: "+ turns.getActualTurn();
		
		if(turns.searchTurn(turns.getActualTurn()) != null) {
			msg += " of the type: " + turns.searchTurn(turns.getActualTurn()).getType().getName().toUpperCase();
		}else {
			msg += " and it was not given to any user.";
		}
		
		return  msg;
	}
	
	public boolean setIfWasServed() throws ParseException, EmptyTurnsException, NoTurnsForAttendException {
		int mins = 0;
		int secs = 0;
		boolean served = false;;
		
		for(int i = 0; i < users.size(); i++) {
			if(users.get(i).getTurn() != null) {
				Turn userTurn = users.get(i).getTurn();
				TurnType userTurnType = userTurn.getType();
				mins = (int) userTurnType.getDuration();
				
				String sSecs = ""+userTurnType.getDuration();
				int index = sSecs.indexOf(".");
				String ssSecs = sSecs.substring((index+1));
				int sec = Integer.parseInt(ssSecs)/10;
				
				secs = 15 + (60*sec);
				
				if(dates.advanceTime(mins, secs).compareTo(getSystemDate()) < 0 && turns.getActualTurn().equals(userTurn.getCode())) {
					int conditioner = (int) (Math.random()*(2-1+1)+1);
					
					if(conditioner == 1) {
						users.get(i).setServed(true);
						users.get(i).setWasAway(false);
						nextTurn();
					}else {
						users.get(i).setServed(true);
						users.get(i).setWasAway(true);
						nextTurn();
					}
					
					served = true;
				}
			}
		}
		
		return served;
	}
	
	public void generateUsers(int c) {
//		TODO
	}
	
	public String getSystemDate() throws ParseException {
		return dates.getSystemDate();
	}
	
	public void updateSystemDate(String date) throws ParseException, EmptyFieldException, InvalidDateException {
		if(date != "") {
			dates.setSystemDate(date);
		}else {
			throw new EmptyFieldException("DATE");
		}
	}

	public void addTurnType(String name, double duration) throws InvalidSelectionException, EmptyFieldException, AlreadyOnListException {
		if(name != "") {
			if(duration != 0.0) {
				turns.addType(name.toUpperCase(), duration);
			}else {
				throw new InvalidSelectionException(duration);
			}
		}else {
			throw new EmptyFieldException("NAME");
		}
	}

	public void textFileUserTurns(String doc, String id) throws FileNotFoundException, IsNotListedException {
		PrintWriter writer = new PrintWriter("data/User's Turns.txt");
		
		writer.println("           USER TURNS           ");
		writer.println("--------------------------------");
		writer.println("--------------------------------");
		
		User toPrint = searchUser(id, doc);
		
		writer.println("\nUser name: "+ toPrint.getName() +" id type: "+ toPrint.getDocType() + " id #: "+ toPrint.getId());
		writer.println("Turn|User Away|Attended");
		writer.println(toPrint.infoToPrintTurns());
		
		writer.close();
	}

	public String consoleUserTurns(String doc, String id) throws IsNotListedException {
		String writer = "";
		
		writer += "           USER TURNS           \n";
		writer += "--------------------------------\n";
		writer += "--------------------------------\n";
		
		User toPrint = searchUser(id, doc);
		
		writer += "User name: "+ toPrint.getName() +" id type: "+ toPrint.getDocType() + " id #: "+ toPrint.getId() +"\n";
		writer += "Turn|User Away|Attended\n";
		writer += toPrint.infoToPrintTurns();
		
		return writer;
	}
	
	public void saveData() throws IOException {
		FileOutputStream fos = new FileOutputStream("data/Persistence.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(users);
		oos.close();
	}

	public void textFileTurnUsers(String code) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter("data/Turn's Users.txt");
		
		writer.println("           TURN USERS           ");
		writer.println("--------------------------------");
		writer.println("--------------------------------");
		writer.println("\nTurn code: "+ code.toUpperCase() + "\n");

		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).binarySearchTurn(code.toUpperCase())) {
				writer.println(users.get(i).info());
			}
		}
		
		writer.close();
	}

	public String consoleTurnUsers(String code) {
		String writer = "";
		
		writer += "           TURN USERS           \n";
		writer += "--------------------------------\n";
		writer += "--------------------------------\n";
		writer += "\nTurn code: "+ code.toUpperCase() + "\n";

		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).binarySearchTurn(code.toUpperCase())) {
				writer += ""+ users.get(i).info() +"\n";
			}
		}
		
		return writer;
	}

	public void selectionSortUsersByIdType() {
		for (int i = 0; i < users.size()-1; i++) {
			User min = users.get(i);
			int pos = i;
			
			for (int j = i + 1; j < users.size(); j++) {
				if (users.get(j).getDocType().compareTo(min.getDocType()) < 0) {
					min = users.get(j);
					pos = j;
				}
				
				User aux = users.get(i);
				users.set(i, min);
				users.set(pos, aux);
			}
		}
	}

	public void sortUsersById() {
		User[] toSort = new User[users.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = users.get(i);
		}
		
		Arrays.sort(toSort, new UserIdComparator());
		
		for (int i = 0; i < toSort.length; i++) {
			users.set(i, toSort[i]);
		}
	}

	public void sortUsersByName() {
		User[] toSort = new User[users.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = users.get(i);
		}
		
		Arrays.sort(toSort);
		
		for (int i = 0; i < toSort.length; i++) {
			users.set(i, toSort[i]);
		}
	}

	public void sortUserBySurname() {
		User[] toSort = new User[users.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = users.get(i);
		}
		
		Arrays.sort(toSort, new Comparator<User>() {

			public int compare(User o1, User o2) {
				int comparation;
				String surname1 = o1.getSurname();
				String surname2 = o2.getSurname();
				
				if(surname1.compareTo(surname2) < 0) {
					comparation = -1;
				}else if(surname1.compareTo(surname2) > 0) {
					comparation = 1;
				}else {
					comparation = 0;
				}
				
				return comparation;
			}});
		
		for (int i = 0; i < toSort.length; i++) {
			users.set(i, toSort[i]);
		}
	}

	public void sortTurnsByType() {
		turns.sortTurnsByType();
	}

	public void sortTurnsByDuration() {
		turns.sortTurnsByDuration();
	}

	public void sortTurnsByCode() {
		turns.sortTurnsByCode();
	}
	

	public void sortUsersByIdInvert() {
		User[] toSort = new User[users.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = users.get(i);
		}
		
		Arrays.sort(toSort, Collections.reverseOrder(new UserIdComparator()));
	}
	
	public String showUsers() {
		String usersToshow = "";
		for (int i = 0; i < users.size(); i++) {
			usersToshow += users.get(i).info() + "\n";
		}
		
		return usersToshow;
	}
	
	public String showTurns() {
		return turns.showTurns();
	}

}
