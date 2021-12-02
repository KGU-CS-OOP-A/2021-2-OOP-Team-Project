package org.corodiak.util;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.corodiak.manager.CustomerManager;
import org.corodiak.manager.OrderController;
import org.corodiak.manager.ProductManager;
import org.corodiak.type.Customer;
import org.corodiak.type.Factory;
import org.corodiak.type.Product;

public class Utils {
	public static LinkedHashMap<String, Integer> sortMapByKey(Map<String, Integer> map, boolean orderBy) {
		//true : ASC, false : DESC
		List<Map.Entry<String, Integer>> entries = new LinkedList<>(map.entrySet());
		if (orderBy) {
			Collections.sort(entries, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
		} else {
			Collections.sort(entries, (o1, o2) -> o2.getKey().compareTo(o1.getKey()));
		}

		LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
		for (Map.Entry<String, Integer> entry : entries) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	public static void dataSetting() {
		ProductManager productManager = ProductManager.getInstance();
		CustomerManager customerManager = CustomerManager.getInstance();
		
		productManager.readAll("menu.txt", new Factory<Product>() {
			@Override
			public Product create() {
				return new Product();
			}
		});
		
		customerManager.readAll("customer.txt", new Factory<Customer>() {			
			@Override
			public Customer create() {
				return new Customer();
			}
		});
		OrderController.getInstance();
	}
}