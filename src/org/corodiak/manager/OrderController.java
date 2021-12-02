package org.corodiak.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import org.corodiak.type.BaeminOrder;
import org.corodiak.type.EatsOrder;
import org.corodiak.type.Factory;
import org.corodiak.type.Order;
import org.corodiak.type.ProductPair;
import org.corodiak.type.YogiyoOrder;
import org.corodiak.util.Utils;

public class OrderController implements OrderService<Order> {

	private static OrderController instance = null;

	private BaeminOrderManager baeminOrderManager;
	private YogiyoOrderManager yogiyoOrderManager;
	private EatsOrderManager eatsOrderManager;

	private List<Order> orderList;

	public static OrderController getInstance() {
		if (instance == null) {
			instance = new OrderController();
		}
		return instance;
	}

	private OrderController() {
		baeminOrderManager = new BaeminOrderManager();
		yogiyoOrderManager = new YogiyoOrderManager();
		eatsOrderManager = new EatsOrderManager();

		baeminOrderManager.readAll("baemin_order.txt", new Factory<BaeminOrder>() {
			@Override
			public BaeminOrder create() {
				return new BaeminOrder();
			}
		});
		yogiyoOrderManager.readAll("yogiyo_order.txt", new Factory<YogiyoOrder>() {
			@Override
			public YogiyoOrder create() {
				return new YogiyoOrder();
			}
		});
		eatsOrderManager.readAll("eats_order.txt", new Factory<EatsOrder>() {
			@Override
			public EatsOrder create() {
				return new EatsOrder();
			}
		});

		orderList = new ArrayList<Order>();
		orderList.addAll(baeminOrderManager.getList());
		orderList.addAll(yogiyoOrderManager.getList());
		orderList.addAll(eatsOrderManager.getList());
		sortByOrderTime(true);
	}

	public List<BaeminOrder> getBaeminOrderList(boolean orderBy) {
		return getBaeminOrderList(LocalDate.MIN, LocalDate.MAX, orderBy);
	}

	public List<BaeminOrder> getBaeminOrderList(LocalDate start, LocalDate end, boolean orderBy) {
		baeminOrderManager.sortByOrderTime(orderBy);
		return baeminOrderManager.getList();
	}

	public List<YogiyoOrder> getYogiyoOrderList(boolean orderBy) {
		return getYogiyoOrderList(LocalDate.MIN, LocalDate.MAX, orderBy);
	}

	public List<YogiyoOrder> getYogiyoOrderList(LocalDate start, LocalDate end, boolean orderBy) {
		yogiyoOrderManager.sortByOrderTime(orderBy);
		return yogiyoOrderManager.getList();
	}

	public List<EatsOrder> getEatsOrderList(boolean orderBy) {
		return getEatsOrderList(LocalDate.MIN, LocalDate.MAX, orderBy);
	}

	public List<EatsOrder> getEatsOrderList(LocalDate start, LocalDate end, boolean orderBy) {
		eatsOrderManager.sortByOrderTime(orderBy);
		return eatsOrderManager.getList();
	}

	public List<Order> getOrderList(boolean orderBy) {
		return getOrderList(LocalDate.MIN, LocalDate.MAX, orderBy);
	}

	public List<Order> getOrderList(LocalDate start, LocalDate end, boolean orderBy) {
		sortByOrderTime(orderBy);
		return selectOrderByDate(orderList, start, end);
	}

	private List<Order> selectOrderByDate(List<Order> orderList, LocalDate start, LocalDate end) {
		List<Order> results = new ArrayList<Order>();
		for (Order order : orderList) {
			LocalDate orderDate = order.getOrderTime().toLocalDate();
			if (orderDate.isAfter(start) && orderDate.isBefore(end))
				results.add(order);
		}
		return results;
	}

	public LinkedHashMap<String, Integer> getBaeminSalesStaticByDate(LocalDate start, LocalDate end) {
		return baeminOrderManager.getSalesStaticByDate(start, end);
	}

	public LinkedHashMap<String, Integer> getYogiyoSalesStaticByDate(LocalDate start, LocalDate end) {
		return yogiyoOrderManager.getSalesStaticByDate(start, end);
	}

	public LinkedHashMap<String, Integer> getEatsSalesStaticByDate(LocalDate start, LocalDate end) {
		return eatsOrderManager.getSalesStaticByDate(start, end);
	}
	// 병합

	public LinkedHashMap<String, Integer> getBaeminSalesStaticByHour(LocalDate start, LocalDate end) {
		return baeminOrderManager.getSalesStaticByHour(start, end);
	}

	public LinkedHashMap<String, Integer> getYogiyoSalesStaticByHour(LocalDate start, LocalDate end) {
		return yogiyoOrderManager.getSalesStaticByHour(start, end);
	}

	public LinkedHashMap<String, Integer> getEatsSalesStaticByHour(LocalDate start, LocalDate end) {
		return eatsOrderManager.getSalesStaticByHour(start, end);
	}
	// 병합

	public LinkedHashMap<String, Integer> getBaeminSalesStaticByProduct(LocalDate start, LocalDate end) {
		return baeminOrderManager.getSalesStaticByProduct(start, end);
	}

	public LinkedHashMap<String, Integer> getYogiyoSalesStaticByProduct(LocalDate start, LocalDate end) {
		return yogiyoOrderManager.getSalesStaticByProduct(start, end);
	}

	public LinkedHashMap<String, Integer> getEatsSalesStaticByProduct(LocalDate start, LocalDate end) {
		return eatsOrderManager.getSalesStaticByProduct(start, end);
	}
	// 병합

	public LinkedHashMap<String, Integer> getBaeminSalesStaticByCustomer(LocalDate start, LocalDate end) {
		return baeminOrderManager.getSalesStaticByCustomer(start, end);
	}

	public LinkedHashMap<String, Integer> getYogiyoSalesStaticByCustomer(LocalDate start, LocalDate end) {
		return yogiyoOrderManager.getSalesStaticByCustomer(start, end);
	}

	public LinkedHashMap<String, Integer> getEatsSalesStaticByCustomer(LocalDate start, LocalDate end) {
		return eatsOrderManager.getSalesStaticByCustomer(start, end);
	}
	// 병합

	@Override
	public void sortByOrderTime(boolean flag) {
		Comparator<Order> comparator = new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				if (o1.getOrderTime().isAfter(o2.getOrderTime()))
					return 1;
				else if (o2.getOrderTime().isAfter(o1.getOrderTime()))
					return -1;
				else
					return 0;
			}
		};
		if (flag) {
			orderList.sort(comparator);
		} else {
			orderList.sort(comparator.reversed());
		}
	}

	// 사용하지 않는 함수
	@Override
	public void customerProcess() {
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByDate(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();

		for (Order order : orderList) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if (!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String date = localDate.toString();
			if (results.containsKey(date)) {
				results.put(date, results.get(date) + order.getProfit());
			} else {
				results.put(date, order.getProfit());
			}
		}
		results = Utils.sortMapByKey(results, true);
		return results;
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByHour(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();

		for (Order order : orderList) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if (!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String time = String.format("%02d", order.getOrderTime().toLocalTime().getHour());
			if (results.containsKey(time)) {
				results.put(time, results.get(time) + order.getProfit());
			} else {
				results.put(time, order.getProfit());
			}
		}

		results = Utils.sortMapByKey(results, true);
		return results;
	}

	@Override
	public LinkedHashMap<String, Integer> getSalesStaticByProduct(LocalDate start, LocalDate end) {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();

		for (Order order : orderList) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if (!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			for (ProductPair pair : order.getProductList()) {
				String productName = pair.getProduct().getName();
				if (results.containsKey(productName)) {
					results.put(productName, results.get(productName) + pair.getQuantity());
				} else {
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

		for (Order order : orderList) {
			LocalDate localDate = order.getOrderTime().toLocalDate();
			if (!(localDate.isAfter(start) && localDate.isBefore(end))) {
				continue;
			}
			String phoneNumber = order.getPhoneNumber();
			String name = customerManager.findCustomerByPhoneNumber(phoneNumber).getName()
					+ phoneNumber.substring(phoneNumber.length() - 4);
			if (results.containsKey(name)) {
				results.put(name, results.get(name) + order.getTotalPrice());
			} else {
				results.put(name, order.getTotalPrice());
			}
		}
		results = Utils.sortMapByKey(results, true);
		return results;
	}

	public void addOrder(Order order) {
		if (order instanceof BaeminOrder) {
			baeminOrderManager.add((BaeminOrder) order);
			baeminOrderManager.customerProcess();
		} else if (order instanceof YogiyoOrder) {
			yogiyoOrderManager.add((YogiyoOrder) order);
			yogiyoOrderManager.customerProcess();
		} else if (order instanceof EatsOrder) {
			eatsOrderManager.add((EatsOrder) order);
			eatsOrderManager.customerProcess();
		}
		orderList.add(order);
	}

	public void deleteOrder(Order order) {
		if (order instanceof BaeminOrder) {
			baeminOrderManager.delete((BaeminOrder) order);
		} else if (order instanceof YogiyoOrder) {
			yogiyoOrderManager.delete((YogiyoOrder) order);
		} else if (order instanceof EatsOrder) {
			eatsOrderManager.delete((EatsOrder) order);
		}
		orderList.remove(order);
	}

	public void deleteOrder(String code) {
		Order order = null;
		for (Order o : orderList) {
			if (o.getCode().equals(code)) {
				order = o;
				break;
			}
		}
		if(order == null)
			return;
		if (order instanceof BaeminOrder) {
			baeminOrderManager.delete((BaeminOrder) order);
		} else if (order instanceof YogiyoOrder) {
			yogiyoOrderManager.delete((YogiyoOrder) order);
		} else if (order instanceof EatsOrder) {
			eatsOrderManager.delete((EatsOrder) order);
		}
		orderList.remove(order);
	}

	public Order findOrder(String kwd) {
		for (Order order : orderList) {
			if (order.matches(kwd))
				return order;
		}
		return null;
	}
}
