package tdd.vendingMachine;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;


/**
 * @author Marek Kort
 *
 */
public class VendingMachineTest {

	private VendingMachine machine;
	
	private Shelve shelve;
	
	private Product product;
	
	private DecimalFormat df = new DecimalFormat("#.00"); 
	
	
	@Before
	public void init(){
		machine = new VendingMachine(20);
		shelve = new Shelve(); 
		product = new Product("Cola 0.33 can", 2.00f);
		machine.setShelve(1, shelve);
		shelve.putProduct(product);
	}
	
    @Test
    public void just_a_stupid_passing_test_to_ensure_that_tests_are_run() {
        Assertions.assertThat(machine).isNotNull();
    }
    
    @Test
    public void testSelectProduct(){
    	machine.selectShelve(1);
    	assertEquals(machine.getDisplay().getMessage(),this.productDescription(product));
    }
    
    @Test
    public void testMessageOnEmptyShelve(){
    	machine.selectShelve(5);
    	assertEquals(machine.getDisplay().getMessage(),VendingMachine.STR_NO_PRODUCT);
    }
    
    @Test
    public void testFirstInsertCoin(){
    	machine.selectShelve(1);
    	machine.putCoin(Nominal.ONE);
    	
    	product.setPrice(product.getPrice()-Nominal.ONE.value());
    	assertEquals(machine.getDisplay().getMessage(),this.productDescription(product));
    }
    
    @Test
    public void testChangeReturn(){
    	Map<Nominal,Integer> initNominals = new HashMap<>();
    	initNominals.put(Nominal.TWO, 1);
    	initNominals.put(Nominal.ONE, 1);
    	machine.setNominals(initNominals);
    	machine.selectShelve(1);

    	machine.putCoin(Nominal.FIVE);
    	
    	assertEquals(machine.getUserBalance(), 5.00f, 0);
    	Map<Nominal,Integer> change = machine.getChange();
    	
    	
    	assertEquals(change.get(Nominal.ONE).intValue(), 1);
    	assertEquals(change.get(Nominal.TWO).intValue(), 1);
    	assertEquals(machine.getUserBalance(), 0f,0);
    }
    
    @Test
    public void testChangeReturn2(){
    	Map<Nominal,Integer> initNominals = new HashMap<>();
    	initNominals.put(Nominal.ZERO_FIFTY, 3);
    	initNominals.put(Nominal.ZERO_TWENTY, 4);
    	initNominals.put(Nominal.ZERO_TEN, 10);
    	
    	machine.setNominals(initNominals);
    	machine.selectShelve(1);
    	
    	
    	
    	machine.putCoin(Nominal.FIVE);
    	
    	assertEquals(machine.getUserBalance(), 5.00f, 0);
    	Map<Nominal,Integer> change = machine.getChange();
    	
    	
    	assertEquals(change.get(Nominal.ZERO_FIFTY).intValue(), 3);
    	assertEquals(change.get(Nominal.ZERO_TWENTY).intValue(), 4);
    	assertEquals(change.get(Nominal.ZERO_TEN).intValue(), 7);
    	assertEquals(machine.getUserBalance(), 0f,0);
    }
    
    
    @Test
    public void testNoChange(){
    	Map<Nominal,Integer> initNominals = new HashMap<>();
    	initNominals.put(Nominal.TWO, 2);
    	machine.selectShelve(1);
    	
    	machine.putCoin(Nominal.FIVE);
    	assertEquals(machine.getDisplay().getMessage(), VendingMachine.STR_NO_CHANGE);
    	
    	Map<Nominal,Integer> change = machine.cancel();
 
    	
    	assertEquals(change.get(Nominal.FIVE).intValue(), 1);
    	assertEquals(machine.getUserBalance(), 0f,0);
    	
    }
    
    @Test
    public void testCancel(){
    	machine.selectShelve(1);
    	
    	machine.putCoin(Nominal.ONE);
    	
    	machine.cancel();
    	
    	assertEquals(machine.getUserBalance(), 0f,0);
    }
    
    
    private String productDescription(Product product){
    	return product.getName() + " " + df.format(product.getPrice());	
    }
    
}
