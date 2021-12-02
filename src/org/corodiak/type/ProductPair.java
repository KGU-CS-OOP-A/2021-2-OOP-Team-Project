package org.corodiak.type;

public class ProductPair {
	
	private Product product;
	private int quantity;
	public Product getProduct() {
	
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "ProductPair [product=" + product + ", quantity=" + quantity + "]";
	}
	
	public int getPrice() {
		return product.getPrice() * quantity;
	}
}
