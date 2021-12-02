package org.corodiak.type;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Order implements Manageable {
	
	private String code;
	private String phoneNumber;
	private int totalPrice;
	private List<ProductPair> productList = new ArrayList<ProductPair>();
	private int paymentMethod;
	private LocalDateTime orderTime;
	
	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ProductPair> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductPair> productList) {
		this.productList = productList;
	}
	
	public int getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public void addProductPair(ProductPair productPair) {
		for(ProductPair p:productList) {
			if(p.getProduct().matches(productPair.getProduct().getCode())) {
				p.setQuantity(p.getQuantity() + productPair.getQuantity());
				return;
			}
		}
		productList.add(productPair);
	}
	
	@Override
	public abstract void read(Scanner scan);

	//소숫점 첫자리에서 반올림
	public abstract int getProfit();
	
	@Override
	public String toString() {
		return "Order [code=" + code + ", phoneNumber=" + phoneNumber + ", totalPrice=" + totalPrice + ", productList="
				+ productList + ", paymentMethod=" + paymentMethod + ", orderTime=" + orderTime + "]";
	}

	protected abstract int parsePayment(String payment);
	
	@Override
	public boolean matches(String kwd) {
		return this.code.equals(kwd);
	}
	
	

}
