package org.corodiak.manager;

import java.time.LocalDate;
import java.util.LinkedHashMap;

import org.corodiak.type.Order;

public interface OrderService<T extends Order> {
	
	void sortByOrderTime(boolean flag);
	void customerProcess();
	LinkedHashMap<String, Integer> getSalesStaticByDate(LocalDate start, LocalDate end);
	LinkedHashMap<String, Integer> getSalesStaticByHour(LocalDate start, LocalDate end);
	LinkedHashMap<String, Integer> getSalesStaticByProduct(LocalDate start, LocalDate end);
	LinkedHashMap<String, Integer> getSalesStaticByCustomer(LocalDate start, LocalDate end);
	
	/*
	 * ��¥�� ��� : ������ ���� ������ �Ϻ� ���� �׷���
	 * �ð��뺰 ��� : ������ ���� ������ �ð��뺰 ���� �׷���
	 * �޴���� : ������ ���� ������ �� �޴��� �Ǹŷ� �׷���
	 * �մ���� : ������ ���� ������ �մԺ� ���� �׷���
	 */
	
}
