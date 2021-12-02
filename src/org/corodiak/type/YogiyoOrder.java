package org.corodiak.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.corodiak.manager.ProductManager;

/*
 * ���� ����
 * �ֹ��ð�(yyyy-MM-dd_HH:mm:ss)/�ֹ�����ȭ��ȣ/��������/���ֹ��ݾ�/�ֹ��Ϸù�ȣ/ǰ���ڵ�_����......
 * �������� : cash, card, coupon
 * �⺻������ : 5%
 * ī�� ������ ������ 3% �߰�
 * �⺻ ��� ������ 1000��
 * ���� �ֹ��� ������ 0
 * 
 */
public class YogiyoOrder extends Order {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
	private static final double PLATFORM_FEE = 0.05;
	private static final double CARD_PAYMENT_FEE = 0.03;
	private static final int DELIVERY_FEE = 1000;

	@Override
	public void read(Scanner scan) {
		String[] data = scan.nextLine().split("/");
		setOrderTime(LocalDateTime.parse(data[0], FORMATTER));
		setPhoneNumber(data[1]);
		setPaymentMethod(parsePayment(data[2]));
		setTotalPrice(Integer.parseInt(data[3]));
		setCode(data[4]);
		
		String[] pair = null;
		ProductPair productPair = null;
		ProductManager productManager = ProductManager.getInstance();
		for(int i=5;i<data.length;i++) {
			pair = data[i].split("_");
			productPair = new ProductPair();
			productPair.setProduct(productManager.find(pair[0]));
			productPair.setQuantity(Integer.parseInt(pair[1]));
			addProductPair(productPair);
		}
	}

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
		return "YogiyoOrder [toString()=" + super.toString() + "]";
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
