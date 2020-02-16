package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUser {
	private User us;
	
	public void setupStage1() {
		us = new User("CC", "1193151954", "Alejandro", "Garcia", "3114209888", "cra 61 #18-16", false);
	}

	@Test
	public void testUser() {
		setupStage1();
		
		String expected = "CC";
		
		assertEquals(expected, us.getDocType());
		
		expected = "1193151954";
		
		assertEquals(expected, us.getId());
		
		expected = "Alejandro";
		
		assertEquals(expected, us.getName());
		
		expected = "Garcia";
		
		assertEquals(expected, us.getSurname());
		
		expected = "3114209888";
		
		assertEquals(expected, us.getPhone());

		expected = "cra 61 #18-16";
		
		assertEquals(expected, us.getAdress());

		boolean expected1 = false; 
		
		assertEquals(expected1, us.wasServed());
	}
	
	@Test
	public void testToString() {
		setupStage1();

		String toStringExpected = "" + "CC" + "||	" + "Alejandro" + " " + "Garcia" + "||	" + "3114209888" + "||	" + null;
		
		assertEquals(toStringExpected, us.toString());
	}
	
	@Test
	public void testSetTurn() {
		setupStage1();
		
		us.setTurn(new Turn("A00"));

		String expected = "A00";
				
		assertEquals(expected, us.getTurn());
	}
	
	@Test
	public void testSetServed() {
		setupStage1();
		
		us.setServed(true);

		boolean expected = true;
				
		assertEquals(expected, us.wasServed());
	}
}
