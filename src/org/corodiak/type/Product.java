package org.corodiak.type;

import java.util.Scanner;

public class Product implements Manageable {

	private String code;
	private String name;
	private int price;

	public Product() {}
	
	@Override
	public boolean matches(String kwd) {
		return this.code.equals(kwd);
	}
	
	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name + ", price=" + price + "]";
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public void read(Scanner scan) {
		String[] data = scan.nextLine().split(" ");
		code = data[0];
		name = data[1];
		price = Integer.parseInt(data[2]);
	}
}
