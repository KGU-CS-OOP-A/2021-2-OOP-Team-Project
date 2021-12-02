package org.corodiak.gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.corodiak.manager.OrderController;
import org.corodiak.type.Order;
import org.corodiak.type.ProductPair;

public class Table {
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane scrollPane;

	public Table(String[][] contents, String[] header) {
		dtm = new DefaultTableModel(contents, header) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7050997678029255961L;

			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		table = new JTable(dtm);
		scrollPane = new JScrollPane(table);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable t = (JTable) e.getSource();
				if (e.getClickCount() == 2 && t.getColumnCount() == 5) {
					OrderController orderController = OrderController.getInstance();
					Order o = orderController.findOrder(dtm.getValueAt(table.getSelectedRow(), 0).toString());
					String paymentMethod = null;
					switch (o.getPaymentMethod()) {
					case 1:
						paymentMethod = "����";
						break;
					case 2:
						paymentMethod = "ī��";
						break;
					case 3:
						paymentMethod = "����";
						break;
					}
					String orderProduct = "";
					List<ProductPair> productPairList = o.getProductList();
					for (ProductPair p : productPairList) {
						orderProduct = orderProduct + p.getProduct().getName() + " " + Integer.toString(p.getPrice())
								+ " " + Integer.toString(p.getQuantity()) + "��\n";
					}
					JOptionPane.showMessageDialog(t,
							"�ֹ��Ϸù�ȣ : " + o.getCode() + "\n�ֹ��ð� : " + o.getOrderTime() + "\n�մ� ��ȭ��ȣ : "
									+ o.getPhoneNumber() + "\n�������� : " + paymentMethod + "\n�� �ֹ��ݾ� : "
									+ Integer.toString(o.getTotalPrice()) + "\n�ֹ� ǰ��\n" + orderProduct,
							"�ֹ� ��", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public JTable getTable() {
		return table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}
