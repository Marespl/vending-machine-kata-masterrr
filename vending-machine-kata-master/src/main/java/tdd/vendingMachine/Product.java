package tdd.vendingMachine;

/**
 * @author Marek Kort
 *
 */
public class Product {
	
	private String name;
	
	private float price;
	
	public Product(String name, float price){
		this.setName(name);
		this.setPrice(price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
}
