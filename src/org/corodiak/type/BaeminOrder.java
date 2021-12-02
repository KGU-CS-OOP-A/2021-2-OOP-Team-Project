package org.corodiak.type;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.corodiak.manager.ProductManager;


/* 
 * 배달의 민족 오더
 * 
 * 주문일련번호|주문시각(yyyy-MM-dd HH:mm:ss)|결제수단|주문자전화번호|주문쌍갯수|품목코드|수량......
 * 결제수단 : cash, card, coupon
 * 기본수수료 : 6.8%
 * 현금, 카드 수수료 차별 없음
 * 기본배달 수수료 1000원
 * 쿠폰 주문 시 순수익 0
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

	//현재 객체 내부에 있는 정보만으로 연산 처리를 하기 때문에 Factory말고 여기서 처리함
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
