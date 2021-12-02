package org.corodiak.manager;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;

import org.corodiak.type.Customer;
import org.corodiak.type.Factory;
import org.corodiak.type.Order;
import org.corodiak.type.ProductPair;
import org.corodiak.type.YogiyoOrder;
import org.corodiak.util.Utils;

public class YogiyoOrderManager extends Manager<YogiyoOrder> implements OrderService<YogiyoOrder> {
	
	@Override
	public void readAll(String filename, Factory<YogiyoOrder> factory) {
		super.readAll(filename, factory);
		sortByOrderTime(true);
		customerProcess();
	}
	
	@Override
	public void customerProcess() {
		CustomerManager customerManager = CustomerManager.getInstance();
		for(Order order:getList()) {
			String phoneNumber = order.getPhoneNumber();
			if(customerManager.findCustomerByPhoneNumber(phoneNumber) == null) {
				Customer customer = new Customer(phoneNumber);
				customerManager.add(customer);
			}
		}
	}
	
	@Override
	public void sortByOrderTime(boolean flag) {
		Comparator<YogiyoOrder> comparator = new Comparator<YogiyoOrder>() {
			@Override
			public int compare(YogiyoOrder o1, YogiyoOrder o2) {
				if(o1.getOrderTime().isAfter(o2.getOrderTime()))
					return 1;
				else if(o2.getOrderTime().isAfter(o1.getOrderTime()))
					return -1;
				else
					return 0;
			}
		};
		if(flag) {
			getList().sort(comparator);
		}
		else {
			getList().sort(comparator.reversed());
		}
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByDate(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();
		
		for(YogiyoOrder order:getList()) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if(!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String date = localDate.toString();
			if(results.containsKey(date)) {
				results.put(date, results.get(date) + order.getProfit());
			}
			else {
				results.put(date, order.getProfit());
			}
		}
		results = Utils.sortMapByKey(results, true);
		return results;
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByHour(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();
		
		for(YogiyoOrder order:getList()) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if(!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String time = String.format("%02d", order.getOrderTime().toLocalTime().getHour());
			if(results.containsKey(time)) {
				results.put(time, results.get(time) + order.getProfit());
			}
			else {
				results.put(time, order.getProfit());
			}
		}
		
		results = Utils.sortMapByKey(results, true);
		return results;
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByProduct(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();
		
		for(YogiyoOrder order:getList()) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if(!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			for(ProductPair pair:order.getProductList()) {
				String productName = pair.getProduct().getName();
				if(results.containsKey(productName)) {
					results.put(productName, results.get(productName) + pair.getQuantity());
				}
				else {
					results.put(productName, pair.getQuantity());
				}
			}
		}
		results = Utils.sortMapByKey(results, true);
		return results;
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByCustomer(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();
		
		CustomerManager customerManager = CustomerManager.getInstance();
		
		for(YogiyoOrder order:getList()) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if(!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String phoneNumber = order.getPhoneNumber();
			String name = customerManager.findCustomerByPhoneNumber(phoneNumber).getName() + phoneNumber.substring(phoneNumber.length()-4);
			if(results.containsKey(name)) {
				results.put(name, results.get(name) + order.getTotalPrice());
			}
			else {
				results.put(name, order.getTotalPrice());
			}
		}
		results = Utils.sortMapByKey(results, true);
		return results;
	}
}
