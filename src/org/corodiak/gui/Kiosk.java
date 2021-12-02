package org.corodiak.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.corodiak.manager.OrderController;
import org.corodiak.manager.ProductManager;
import org.corodiak.type.BaeminOrder;
import org.corodiak.type.EatsOrder;
import org.corodiak.type.Order;
import org.corodiak.type.Product;
import org.corodiak.type.ProductPair;
import org.corodiak.type.YogiyoOrder;

public class Kiosk {
	ArrayList<Component> components = new ArrayList<Component>();
	public Kiosk() {
		ProductManager productManager = ProductManager.getInstance();
		
		List<ProductPair> productPairList = new ArrayList<ProductPair>();
		List<Product> productList = productManager.getList();
		List<JButton> buttonList = new ArrayList<JButton>();
		
		String method[] = { "Çö±Ý", "Ä«µå", "ÄíÆù" };
		String platform[] = { "¹è´ÞÀÇ¹ÎÁ·", "¿ä±â¿ä", "ÄíÆÎÀÌÃ÷" };
		
		JComboBox<String> combo1 = new JComboBox<String>(method);
		JComboBox<String> combo2 = new JComboBox<String>(platform);
		JButton orderBtn = new JButton("ÁÖ¹®");

		JTextArea order = new JTextArea();
		JTextArea phoneNumber = new JTextArea();
		phoneNumber.setMaximumSize(new Dimension(1000,300));
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		
		panel1.setLayout(new GridLayout(productList.size() / 3, 3));
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.add(phoneNumber);
		panel2.add(combo1);
		panel2.add(combo2);
		panel2.add(orderBtn);
		
		for (int i = 0; i < productList.size(); i++) {
			buttonList.add(new JButton(productList.get(i).getName()));
			buttonList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Product p : productList) {
						if (p.getName() == ((JButton) e.getSource()).getText()) {
							Quantity q = new Quantity(order, p, productPairList);
							break;
						}
					}
				}
			});
			panel1.add(buttonList.get(i));
		}

		orderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (order.getText() == null || phoneNumber.getText() == null)
					return;
				OrderController orderController = OrderController.getInstance();
				Order newOrder;
				switch (combo2.getSelectedIndex()) {
				case 0:
					newOrder = new BaeminOrder();
					break;
				case 1:
					newOrder = new YogiyoOrder();
					break;
				case 2:
					newOrder = new EatsOrder();
					break;
				default:
					return;
				}
				newOrder.setCode(Integer.toString(LocalDateTime.now().hashCode()));
				newOrder.setPhoneNumber(phoneNumber.getText());
				newOrder.setPaymentMethod(combo1.getSelectedIndex() + 1);
				newOrder.setOrderTime(LocalDateTime.now());
				int total=0;
				for(ProductPair pp:productPairList) {
					newOrder.addProductPair(pp);
					total = total + pp.getPrice()*pp.getQuantity();
				}
				newOrder.setTotalPrice(total);
				orderController.addOrder(newOrder);
				order.setText("");
				phoneNumber.setText("");
				productPairList.clear();
			}
		});
		
		order.getBorder();
		components.add(panel1);
		components.add(order);
		components.add(phoneNumber);
		components.add(panel2);
	}
	
	public ArrayList<Component> getComponents(){
		return components;
	}
}
