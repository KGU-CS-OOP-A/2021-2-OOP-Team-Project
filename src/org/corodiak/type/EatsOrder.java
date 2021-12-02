package org.corodiak.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.corodiak.manager.ProductManager;

/*
 * �������� ����
 * �ֹ��Ϸù�ȣ �ֹ�����ȭ��ȣ �������� ���ֹ��ݾ� �ֹ��ð�("yyyyMMddHHmmss") �ֹ��ְ��� ǰ���ڵ� ����......
 * �������� : CSH, CRD, CPN
 * �⺻������ : 10% + 500��
 * ī�� ������ ������ 3% �߰�
 * ��޼����� ����
 * ���� �ֹ��� ������ 0
 * 
 */
public class EatsOrder extends Order {
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private static final double PLATFORM_FEE = 0.068;
	private static final double CARD_PAYMENT_FEE = 0.03;
	private static final int DELIVERY_FEE = 0;
	private static final int DEFAULT_FEE = 500;

	@Override
	public void read(Scanner scan) {
		String[] data = scan.nextLine().split(" ");
		setCode(data[0]);
		setPhoneNumber(data[1]);
		setPaymentMethod(parsePayment(data[2]));
		setTotalPrice(Integer.parseInt(data[3]));
		setOrderTime(LocalDateTime.parse(data[4], FORMATTER));
		
		int pairCount = Integer.parseInt(data[5]);
		ProductPair productPair = null;
		ProductManager productManager = ProductManager.getInstance();
		for(int i=0;i<pairCount;i++) {
			productPair = new ProductPair();
			productPair.setProduct(productManager.find(data[6+(i*2)]));
			productPair.setQuantity(Integer.parseInt(data[6+(i*2 + 1)]));
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
		result = (int)Math.round(result * (1.0 - fee) - DELIVERY_FEE - DEFAULT_FEE);
		
		return result;
	}
	
	@Override
	public String toString() {
		return "EatsOrder [toString()=" + super.toString() + "]";
	}

	@Override
	protected int parsePayment(String payment) {
		switch (payment) {
			case "CSH":
				return 1;
			case "CRD":
				return 2;
			case "CPN":
				return 3;
			default:
				return 1;
		}
	}
}
