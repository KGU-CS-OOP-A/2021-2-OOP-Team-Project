package org.corodiak.manager;

import org.corodiak.type.Customer;

public class CustomerManager extends Manager<Customer> {

	private static CustomerManager instance = null;
	
	public static CustomerManager getInstance() {
		if(instance == null)
			instance = new CustomerManager();
		return instance;
	}
	
	public Customer findCustomerByPhoneNumber(String phoneNumber) {
		for(Customer customer:getList()) {
			if(customer.getPhoneNumber().equals(phoneNumber))
				return customer;
		}
		return null;
	}
	
}
