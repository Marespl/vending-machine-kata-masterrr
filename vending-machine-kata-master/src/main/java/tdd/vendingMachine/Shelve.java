package tdd.vendingMachine;

import java.util.ArrayList;
import java.util.List;

public class Shelve {

	private List<Product> products = new ArrayList<>();
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void putProduct(Product product) {
		if(!products.isEmpty()){
			if(!products.get(0).getName().equals(product.getName())){
				throw new RuntimeException("Different product");
			}
		}
		products.add(product);
	}

	public Product getPrudct() {
		if(products.isEmpty()){
			return null;
		}
		return products.remove(products.size()-1);
	}

	public Product getProductDetails() {
		return products.get(0);
	}

}
