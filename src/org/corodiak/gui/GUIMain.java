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

		String[] menuTitle = { "�޴� ����", "�ֹ� ����", "���� Ȯ��", "�մ� ����" };
		String[][] itemMenuTitle = { { "�޴� ��ȸ" }, { "�ֹ� ��ȸ", "�ֹ� �߰�", "�ֹ� �÷����� ��ȸ" },
				{ "��¥�� ���", "�ð��뺰 ���", "�޴��� ���", "�մԺ� ���" }, { "�մ� ��ȸ" } };

		MenuBar menuBar = new MenuBar(menuTitle, itemMenuTitle);
		frame.setMenuBar(menuBar);

		String title = "�ֹ���ȸ";
		String header[] = { "�ֹ��Ϸù�ȣ", "�ֹ��ð�", "�մ� ��ȭ��ȣ", "��������", "�� �ֹ��ݾ�" };
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
				row.addElement("����");
				break;
			case 2:
				row.addElement("ī��");
				break;
			case 3:
				row.addElement("����");
				break;
			default:
				row.addElement("����");
			}
			row.addElement(Integer.toString(o.getTotalPrice()));
			table.getDtm().addRow(row);
		}

		frame.addInternalFrame(title, table.getScrollPane());
	}
}
