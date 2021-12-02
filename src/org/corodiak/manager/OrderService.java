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
	 * 날짜별 통계 : 지정한 일자 사이의 일별 수익 그래프
	 * 시간대별 통계 : 지정한 일자 사이의 시간대별 수익 그래프
	 * 메뉴통계 : 지정한 일자 사이의 각 메뉴별 판매량 그래프
	 * 손님통계 : 지정한 일자 사이의 손님별 매출 그래프
	 */
	
}
