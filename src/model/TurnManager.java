package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import exceptions.AlreadyOnListException;
import exceptions.EmptyTurnsException;
import exceptions.NoTurnsForAttendException;
import exceptions.UnexistingTypeException;

public class TurnManager {
	private ArrayList<TurnType> types;
	private ArrayList<Turn> turns;
	private String lastTurn;
	private String actualTurn;
	private Dte systemDate;
	
	public TurnManager() {
		types = new ArrayList<TurnType>();
		turns = new ArrayList<Turn>();
		lastTurn = "A0/";
		actualTurn = "A00";
		systemDate = new Dte();
	}
	
	public Turn giveTurn(String tType) throws ParseException, UnexistingTypeException {
		TurnType type = searchTurnType(tType);
		Turn toAdd = null;
		
		if(type != null) {
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
			
			toAdd = new Turn(lastTurn, type, false,systemDate.getSystemDate(), false);
			turns.add(toAdd);
		}else {
			throw new UnexistingTypeException(tType);
		}
		return toAdd;
	}
	
	public Turn nextTurn() throws NoTurnsForAttendException {
		insertionSortTurnsByBuiltD();
		
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
		
		Turn oActualTurn = searchTurn(actualTurn);
		
		if(oActualTurn == null) {
			throw new NoTurnsForAttendException("The next turn is: "+actualTurn+" and it was not given to any user.");
		}
		
		return oActualTurn;
	}
	
	private void insertionSortTurnsByBuiltD() {
		for (int i = 1; i < turns.size(); i++) {
			for (int j = i; j > 0 && turns.get(j - 1).getBuiltDate().compareTo(turns.get(j).getBuiltDate()) > 0; j--) {
				Turn aux = turns.get(j);
				turns.set(j, turns.get(j - 1));
				turns.set(j - 1, aux);
			}
		}
	}

	public TurnType searchTurnType(String name) {
		TurnType aux = null;
		boolean found = false;
		
		for (int i = 0; i < types.size() && !found; i++) {
			if(types.get(i).getName().equals(name)) {
				aux = types.get(i);
				found = true;
			}
		}
		
		return aux;
	}
	
	public Turn searchTurn(String code) {
		Turn aux = null;
		boolean found = false;
		
		for (int i = 0; i < types.size() && !found; i++) {
			if(turns.get(i).getCode().equals(code)) {
				aux = turns.get(i);
				found = true;
			}
		}
		
		return aux;
	}
	
	public String getActualTurn() throws EmptyTurnsException {
		return actualTurn;
	}

	public void addType(String name, double duration) throws AlreadyOnListException {
		if(searchTurnType(name) == null) {
			TurnType aux = new TurnType(name, duration);
			types.add(aux);
		}else {
			throw new AlreadyOnListException(name);
		}
	}
	
	public String showTurns() {
		return turns.toString();
	}

	public void sortTurnsByType() {
		Turn[] toSort = new Turn[turns.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = turns.get(i);
		}
		
		Arrays.sort(toSort);
		
		for (int i = 0; i < toSort.length; i++) {
			turns.set(i, toSort[i]);
		}
	}

	public void sortTurnsByDuration() {
		Turn[] toSort = new Turn[turns.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = turns.get(i);
		}
		
		Arrays.sort(toSort, new TurnDurationComparator());
		
		for (int i = 0; i < toSort.length; i++) {
			turns.set(i, toSort[i]);
		}
	}

	public void sortTurnsByCode() {
		Turn[] toSort = new Turn[turns.size()];
		
		for (int i = 0; i < toSort.length; i++) {
			toSort[i] = turns.get(i);
		}
		
		Arrays.sort(toSort, new TurnCodeComparator());
		
		for (int i = 0; i < toSort.length; i++) {
			turns.set(i, toSort[i]);
		}
	}
}
