package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.IsNotListedException;
import exceptions.NoTurnsForAttendException;
import exceptions.TurnIsNotAttendedExcpetion;

class TestController {
	private Controller control;
	
	public void setupStage1() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, TurnIsNotAttendedExcpetion{
		control = new Controller("A00", "A0/");
		
		String doc = "CC";
		String id = "1193151954";
		String name = "Alejandro";
		String surname = "Garcia";
		String phone = "3114209888";
		String adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		control.giveTurn(id, doc);
	}
	
	public void setupStage2() {
		control = new Controller("A00", "D99");
	}
	
	public void setupStage3() throws AlreadyOnListException, EmptyFieldException, IsNotListedException, TurnIsNotAttendedExcpetion {
		control = new Controller("A00", "Z99");
		control.addUser("CC", "1193151954", "alejandro", "garcia", "3114209888", "cra 61 # 18-16");
	}

	@Test
	public void testAddUser() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, TurnIsNotAttendedExcpetion{
		setupStage1();
		
		assertThrows(EmptyFieldException.class, ()->{control.addUser("", "1234", "Maria", "Garcia", "3146131522", "cra 61 #18-16");});
		
		assertThrows(EmptyFieldException.class, ()->{control.addUser("CC", "", "Maria", "Garcia", "3146131522", "cra 61 #18-16");});
		
		assertThrows(EmptyFieldException.class, ()->{control.addUser("CC", "1234", "", "Garcia", "3146131522", "cra 61 #18-16");});

		assertThrows(EmptyFieldException.class, ()->{control.addUser("CC", "1234", "Maria", "", "3146131522", "cra 61 #18-16");});
		
		String doc = "CC";
		String id = "1193151994";
		String name = "Angel";
		String surname = "Garcia";
		String phone = "3105358790";
		String adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		assertEquals(id, control.searchUser(id, doc).getId());
			
		assertThrows(AlreadyOnListException.class, ()->{control.addUser("CC", "1193151954", "Alejandro", "Garcia", "3114209888", "Cra 61 #18-16");});
		
		setupStage2();
		
		doc = "CC";
		id = "1193151954";
		name = "Alejandro";
		surname = "Garcia";
		phone = "3114209888";
		adress = "Cra 61 #18-16";
		
		control.addUser(doc, id, name, surname, phone, adress);
		assertEquals(id, control.searchUser(id, doc).getId());
	}
	
	@Test
	public void testSearchUser() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, TurnIsNotAttendedExcpetion {
		setupStage2();
		
		String id = "1193151954";
		String doc = "CC";
		
		assertThrows(IsNotListedException.class, ()->{control.searchUser(id, doc);});
		
		setupStage1();
		
		User expected = new User("CC", "1193151954", "Alejandro", "Garcia", "3114209888", "Cra 61 #18-16", false);
		expected.setTurn(new Turn("A00"));
		User actual = control.searchUser(id, doc);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void testGiveTurn() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, TurnIsNotAttendedExcpetion {
		setupStage1();
		
		String id = "1193151954";
		String doc = "CC";
		String expected = "A00";
		
		assertEquals(expected, control.searchUser(id, doc).getTurn());

		assertThrows(TurnIsNotAttendedExcpetion.class, ()->{control.giveTurn(id, doc);});
		
		control.searchUser(id, doc).setTurn(null);
		control.giveTurn(id, doc);
		expected = "A01";
		
		assertEquals(expected, control.searchUser(id, doc).getTurn());
		
		setupStage2();
		
		control.addUser(doc, id, "alejandro", "garcia", "3114209888", "cra 61 # 18-16");
		control.giveTurn(id, doc);
		expected = "E00";
		
		assertEquals(expected, control.searchUser(id, doc).getTurn());
		
		setupStage3();
		
		control.giveTurn("1193151954", "CC");
		expected = "A00";
		
		assertEquals(expected, control.searchUser(id, doc).getTurn());
	}
	
	@Test
	public void testActualandNextTurn() throws EmptyFieldException, AlreadyOnListException, IsNotListedException, TurnIsNotAttendedExcpetion, NoTurnsForAttendException {
		setupStage1();
		
		String expected = "Actual turn is: A00";
		
		assertEquals(expected, control.getActualTurn());
		
		expected = "The next turn is: A01";
		
		assertEquals(expected, control.nextTurn());
		assertThrows(NoTurnsForAttendException.class, ()->{control.nextTurn();});
	}
}
