package org.corodiak.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.corodiak.manager.ProductManager;


/* 
 * ����� ���� ����
 * 
 * �ֹ��Ϸù�ȣ|�ֹ��ð�(yyyy-MM-dd HH:mm:ss)|��������|�ֹ�����ȭ��ȣ|�ֹ��ְ���|ǰ���ڵ�|����......
 * �������� : cash, card, coupon
 * �⺻������ : 6.8%
 * ����, ī�� ������ ���� ����
 * �⺻��� ������ 1000��
 * ���� �ֹ� �� ������ 0
 * 
 */
public class BaeminOrder extends Order {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private static final double PLATFORM_FEE = 0.068;
	private static final double CARD_PAYMENT_FEE = 0.0;
	private static final int DELIVERY_FEE = 1000;
	
	public BaeminOrder() {}

	@Override
	public void read(Scanner scan) {
		String[] data = scan.nextLine().split("\\|");
		setCode(data[0]);
		setOrderTime(LocalDateTime.parse(data[1], FORMATTER));
		setPaymentMethod(parsePayment(data[2]));
		setPhoneNumber(data[3]);
		
		int totalPrice = 0;
		int pairCount = Integer.parseInt(data[4]);
		ProductPair productPair = null;
		ProductManager productManager = ProductManager.getInstance();
		for(int i=0;i<pairCount;i++) {
			productPair = new ProductPair();
			productPair.setProduct(productManager.find(data[5+(i*2)]));
			productPair.setQuantity(Integer.parseInt(data[5+(i*2 + 1)]));
			totalPrice += productPair.getPrice();
			addProductPair(productPair);
		}
		setTotalPrice(totalPrice);
	}

	//���� ��ü ���ο� �ִ� ���������� ���� ó���� �ϱ� ������ Factory���� ���⼭ ó����
	@Override
	public int getProfit() {
		if(getPaymentMethod() == 3) {
			return 0;
		}
		double fee = PLATFORM_FEE;
		if(getPaymentMethod() == 2) {
			fee += CARD_PAYMENT_FEE;
		}
		
		int result = getTotalPrice();
		result = (int)Math.round(result * (1.0 - fee) - DELIVERY_FEE);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "BaeminOrder [toString()=" + super.toString() + "]";
	}
	
	@Override
	public int parsePayment(String payment) {
		switch (payment) {
			case "cash":
				return 1;
			case "card":
				return 2;
			case "coupon":
				return 3;
			default:
				return 1;
		}
	}

}
