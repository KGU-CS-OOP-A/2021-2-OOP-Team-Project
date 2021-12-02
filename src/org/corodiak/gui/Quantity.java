package org.corodiak.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.corodiak.type.Product;
import org.corodiak.type.ProductPair;

public class Quantity {
	public Quantity(JTextArea order, Product p, List<ProductPair> productPairList) {
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3, 3));
		List<JButton> buttonList = new ArrayList<JButton>();
		JPanel panel2 = new JPanel();
		JTextField quantity = new JTextField();
		JButton submitBtn = new JButton("확인");
		for (int i = 0; i < 9; i++) {
			buttonList.add(new JButton((i + 1) + ""));
			buttonList.get(i).addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					quantity.setText(quantity.getText() + ((JButton) e.getSource()).getText());
				}
			});
			panel1.add(buttonList.get(i));
		}
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		panel2.setMaximumSize(new Dimension(1000, 300));
		panel2.add(quantity);
		panel2.add(submitBtn);

		ArrayList<Component> components = new ArrayList<Component>();
		components.add(panel1);
		components.add(panel2);

		GUIMain.frame.addInternalFrame("수량", components);
		JInternalFrame internalFrame = GUIMain.frame.getInternalFrame();
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProductPair pp = new ProductPair();
				pp.setProduct(p);
				pp.setQuantity(Integer.parseInt(quantity.getText()));
				productPairList.add(pp);
				order.setText(order.getText() + pp.getProduct().getName() + "\t" + pp.getQuantity() + "\t"
						+ pp.getPrice() + "\r\n");
				internalFrame.dispose();
			}
		});
	}
}
