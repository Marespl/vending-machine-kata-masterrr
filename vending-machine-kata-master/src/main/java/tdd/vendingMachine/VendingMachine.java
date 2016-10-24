package tdd.vendingMachine;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Marek Kort
 *
 */
public class VendingMachine {

	public static final String STR_NO_CHANGE = "No change";

	private static final String SELECT_PRODUCT = "Select product";

	public static final String STR_NO_PRODUCT = "No product";
	
	private Display display;

	private Shelve[] shelves;
	
	private Product selectedProduct;
	
	private Map<Nominal,Integer> nominals = new HashMap<>();
	
	private DecimalFormat df = new DecimalFormat("#.00"); 
	
	private List<Nominal> coinOperations = new ArrayList<>();
	
	private float userBalance = 0;
	
	private boolean isCanceled = false;

	public VendingMachine(int shelvesNo){
		setShelves(new Shelve[shelvesNo]);
		this.setDisplay(new Display());
		this.getDisplay().setMessage(SELECT_PRODUCT);
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public void setShelve(int shelveNo, Shelve shelve) {
		shelves[shelveNo-1] = shelve;
	}

	public Shelve[] getShelves() {
		return shelves;
	}

	public void setShelves(Shelve[] shelves) {
		this.shelves = shelves;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}
	


	public Map<Nominal,Integer> getNominals() {
		return nominals;
	}

	public void setNominals(Map<Nominal,Integer> nominals) {
		this.nominals = nominals;
	}
	
	public void setUserBalance(float userBalance) {
		this.userBalance = userBalance;
	}
	
	public float getUserBalance() {
		return this.userBalance;
	}

	public List<Nominal> getCoinOperations() {
		return coinOperations;
	}

	public void setCoinOperations(List<Nominal> coinOperations) {
		this.coinOperations = coinOperations;
	}
	
	/**
	 * Zaznaczenie numeru półki przez użytkownika
	 * 
	 * @param i
	 */
	public void selectShelve(int i) {
		if(i <= 0 || i > this.shelves.length){
			this.getDisplay().setMessage(STR_NO_PRODUCT);
			return;
		}
		Shelve shelve = this.shelves[i-1];
		
		if(shelve == null){
			this.getDisplay().setMessage(STR_NO_PRODUCT);
			return;
		}
		
		if(shelve.getProducts().isEmpty()){
			this.getDisplay().setMessage(STR_NO_PRODUCT);
		} else {
			this.setSelectedProduct(shelve.getProductDetails());
			this.setDisplayMessage();
		}
	}

	/**
	 * Wrzucenie monety przez użytkownika do maszyny
	 * 
	 * @param nominal
	 */
	public void putCoin(Nominal nominal) {
		this.getCoinOperations().add(nominal);
		
		this.setUserBalance(this.getUserBalance() + nominal.value());
		Integer current = this.getNominals().get(nominal);
		if(current==null || current==0){
			this.getNominals().put(nominal, 1);
		} else {
			this.getNominals().put(nominal, current++);
		}
		
		if(this.checkChange() == null){
			this.getDisplay().setMessage(STR_NO_CHANGE);
		} else {
			this.setDisplayMessage();
		}
		
	}

	/**
	 * Sprawdza czy automat ma wydać resztę - jeżeli zwraca null to nie może wydać reszty
	 * 
	 * @return
	 */
	public Map<Nominal, Integer> checkChange() {
		Map<Nominal,Integer> result = new HashMap<>();
		float change = this.getUserBalance() - this.getSelectedProduct().getPrice();
		if(change > 0){
			
			Integer nominalCount;
			int help;
			for(Nominal nominal : Nominal.values()){
				nominalCount = this.getNominals().get(nominal);
				if(nominal.value() <= change && nominalCount != null && nominalCount>0){
					help = (int)(change/nominal.value());
					if(help > nominalCount){
						result.put(nominal, nominalCount);
						change -= nominalCount * nominal.value();
					} else {
						result.put(nominal, help);
						change -= help * nominal.value();
					}
				}
			}
			if(change > 0){
				return null;
			}
		} 
		return result;
	}
	
	/**
	 * Zwrócenie reszty
	 * 
	 * @return
	 */
	public Map<Nominal, Integer> getChange() {
		Map<Nominal,Integer> changeToRemove = this.checkChange();
		
		if(changeToRemove == null){
			this.setUserBalance(0);
			return userCoins();
		}
		
		Integer currentCount;
		for(Nominal nominal : changeToRemove.keySet()){
			currentCount = this.getNominals().get(nominal);
			currentCount -= changeToRemove.get(nominal);
			this.getNominals().put(nominal, currentCount);
		}
		
		this.setUserBalance(0);
		return changeToRemove;
	}

	

	/**
	 * Zwrocenie monet wrzuconych przez uzytkownika
	 * 
	 * @return
	 */
	private Map<Nominal, Integer> userCoins() {
		Map<Nominal,Integer> result = new HashMap<>();
		Integer cc;
		Integer ccMachine;
		for(Nominal nominal : this.getCoinOperations()){
			cc = result.get(nominal);
			ccMachine = this.getNominals().get(nominal);
			if(cc == null){
				result.put(nominal, 1);
			} else {
				result.put(nominal, cc++);
			}
			this.getNominals().put(nominal, ccMachine--);
		}
		this.getCoinOperations().clear();
		return result;
	}

	/**
	 * Anulowanie operacji
	 * 
	 * @return
	 */
	public Map<Nominal, Integer> cancel() {
		this.setUserBalance(0f);
		this.setSelectedProduct(null);
		this.getDisplay().setMessage(SELECT_PRODUCT);
		return this.userCoins();
	}

	private void setDisplayMessage(){
		String message = this.getSelectedProduct().getName() + " " + df.format(this.getSelectedProduct().getPrice() - this.getUserBalance());	
		this.getDisplay().setMessage(message);
	}



}
