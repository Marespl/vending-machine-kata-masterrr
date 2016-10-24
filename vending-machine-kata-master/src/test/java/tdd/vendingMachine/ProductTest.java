package tdd.vendingMachine;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Marek Kort
 *
 */
public class ProductTest {

	private Product product;
	
	@Before
	public void init(){
		product = new Product("Cola 0.33 can", 2.00f);
	}
	
	@Test
	public void productConstructorTest(){
		assertEquals(product.getPrice(), 2.00f, 0);
		assertEquals(product.getName(), "Cola 0.33 can");
	}
}
