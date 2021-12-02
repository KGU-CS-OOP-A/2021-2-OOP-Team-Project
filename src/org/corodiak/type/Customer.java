package org.corodiak.type;

import java.util.Random;
import java.util.Scanner;

public class Customer implements Manageable {
	
	private String code;
	private String name;
	private String phoneNumber;
	
	public Customer() {}
	
	public Customer(String code, String name, String phoneNumber) {
		this.code = code;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}
	
	public Customer(String phoneNumber) {
		this.code = generateCode();
		this.name = "NONAMED";
		this.phoneNumber = phoneNumber;
	}
	

	@Override
	public void read(Scanner scan) {
		String[] data = scan.nextLine().split("_");
		code = data[0];
		name = data[1];
		phoneNumber = data[2];
	}

	@Override
	public boolean matches(String kwd) {
		return this.code.equals(kwd);
	}
	
	private String generateCode() {
		
		char[] arr = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		
		for(int i=0;i<10;i++)
			sb.append(arr[random.nextInt(arr.length)]);
		
		return sb.toString();
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "Customer [code=" + code + ", name=" + name + ", phoneNumber=" + phoneNumber + "]";
	}
}
