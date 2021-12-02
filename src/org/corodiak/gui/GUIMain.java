package org.corodiak.gui;

import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

import org.corodiak.manager.OrderController;
import org.corodiak.type.Order;

public class GUIMain {
	static Frame frame;

	public static void startGUI() {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {
		frame = new Frame();

		String[] menuTitle = { "메뉴 관리", "주문 관리", "매출 확인", "손님 관리" };
		String[][] itemMenuTitle = { { "메뉴 조회" }, { "주문 조회", "주문 추가", "주문 플랫폼별 조회" },
				{ "날짜별 통계", "시간대별 통계", "메뉴별 통계", "손님별 통계" }, { "손님 조회" } };

		MenuBar menuBar = new MenuBar(menuTitle, itemMenuTitle);
		frame.setMenuBar(menuBar);

		String title = "주문조회";
		String header[] = { "주문일련번호", "주문시간", "손님 전화번호", "결제수단", "총 주문금액" };
		String[][] contents = new String[0][5];
		Table table = new Table(contents, header);

		OrderController orderController = OrderController.getInstance();
		List<Order> orderList = orderController.getOrderList(LocalDate.now().plusDays(-3), LocalDate.now().plusDays(1),
				false);

		for (Order o : orderList) {
			Vector<String> row = new Vector<String>();
			row.addElement(o.getCode());
			row.addElement(o.getOrderTime().toString());
			row.addElement(o.getPhoneNumber());
			switch (o.getPaymentMethod()) {
			case 1:
				row.addElement("현금");
				break;
			case 2:
				row.addElement("카드");
				break;
			case 3:
				row.addElement("쿠폰");
				break;
			default:
				row.addElement("현금");
			}
			row.addElement(Integer.toString(o.getTotalPrice()));
			table.getDtm().addRow(row);
		}

		frame.addInternalFrame(title, table.getScrollPane());
	}
}
