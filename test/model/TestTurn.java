package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestTurn {
	private Turn t;
	
	public void setupStage1() {
		t = new Turn("A00");
	}

	@Test
	public void testTurn() {
		setupStage1();
		
		String expected = "A00";
		
		assertEquals(expected, t.getCode());
	}

}
