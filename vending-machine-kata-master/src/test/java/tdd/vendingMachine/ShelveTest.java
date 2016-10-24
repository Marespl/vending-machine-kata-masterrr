package tdd.vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author Marek Kort
 *
 */
public class ShelveTest {

	private Shelve shelve;
	
	private Product product;
	
	 @Rule
	 public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void init(){
		shelve = new Shelve();
		product = new Product("Cola 0.33 can", 2.00f);
		shelve.putProduct(product);
	}
	
	@Test
	public void testPutProduct(){
		assertEquals(shelve.getPrudct(),product);
	}
	
	@Test
	public void checkExceptionThrowWhenDifferentType(){
		exception.expect(RuntimeException.class);
		shelve.putProduct(new Product("Different product", 0.0f));
	}
	
	@Test
	public void checkNullIfShelveEmpty(){
		shelve.getPrudct();
		assertNull(shelve.getPrudct());
	}
}
