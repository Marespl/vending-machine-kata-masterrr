package tdd.vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marek Kort
 *
 */
public class DisplayTest {
	
	private Display display;
	
	@Before
	public void init(){
		display = new Display();
		display.setMessage("Price 2.50");
	}

	@Test
	public void messageTest(){
		assertEquals("Price 2.50", display.getMessage());
	}
}
