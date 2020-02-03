package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.IsNotListedException;
import exceptions.UserWithoutTurnException;

class TestController {
	private Controller control;
	
	public void setupStage1() throws EmptyFieldException, AlreadyOnListException, IsNotListedException{
		control = new Controller("A00");
		
		String doc = "CC";
		String id = "1193151954";
		String name = "Alejandro";
		String surname = "Garcia";
		String phone = "3114209888";
		String adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		try {
			control.getUserTurn(id);
		}catch (UserWithoutTurnException e) {
			control.giveTurn(id);
		}
	}
	
	public void setupStage2() {
		control = new Controller("A00");
	}

	@Test
	public void testAddUser() throws EmptyFieldException, AlreadyOnListException, IsNotListedException{
		setupStage1();
		
		String doc = "CC";
		String id = "1193151994";
		String name = "Angel";
		String surname = "Garcia";
		String phone = "3105358790";
		String adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		assertEquals(id, control.searchUser(id).getId());
			
		assertThrows(AlreadyOnListException.class, ()->{control.addUser("CC", "1193151954", "Alejandro", "Garcia", "3114209888", "Cra 61 #18-16");});
		
		setupStage2();
		
		doc = "CC";
		id = "1193151954";
		name = "Alejandro";
		surname = "Garcia";
		phone = "3114209888";
		adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		assertEquals(id, control.searchUser(id).getId());
	}
	
	@Test
	public void testSearchUser() throws EmptyFieldException, AlreadyOnListException, IsNotListedException {
		setupStage2();
		
		String id = "1193151954";
		
		assertThrows(IsNotListedException.class, ()->{control.searchUser(id);});
		
		setupStage1();
		
		User expected = new User("CC", "1193151954", "Alejandro", "Garcia", "3114209888", "Cra 61 #18-16", null, false);
		
		assertEquals(expected.toString(), control.searchUser(expected.getId()).toString(), "The user expected is not the same that the program found");
	}
	
	@Test
	public void testGiveTurn() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, UserWithoutTurnException {
		setupStage1();
		
		String id = "1193151954";
		String expected = control.searchUser(id).toString()+"		"+"A00";
		
		assertEquals(expected, control.getUserTurn(id));
		
		
	}
}
