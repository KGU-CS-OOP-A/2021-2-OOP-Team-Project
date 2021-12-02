package org.corodiak.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.corodiak.manager.CustomerManager;
import org.corodiak.manager.OrderController;
import org.corodiak.manager.ProductManager;
import org.corodiak.type.BaeminOrder;
import org.corodiak.type.Customer;
import org.corodiak.type.EatsOrder;
import org.corodiak.type.Order;
import org.corodiak.type.Product;
import org.corodiak.type.YogiyoOrder;

import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class MenuBar implements ActionListener {
	private JMenuBar menuBar;
	private JMenu[] menus;
	private ArrayList<JMenuItem> menuItems = new ArrayList<JMenuItem>();

	public MenuBar(String[] menuTitle, String[][] itemMenuTitle) {
		menuBar = new JMenuBar();
		menus = new JMenu[menuTitle.length];
		for (int i = 0; i < menuTitle.length; i++) {
			menus[i] = new JMenu(menuTitle[i]);
			menuBar.add(menus[i]);
			for (int j = 0; j < itemMenuTitle[i].length; j++) {
				menuItems.add(new JMenuItem(itemMenuTitle[i][j]));
				menuItems.get(menuItems.size() - 1).addActionListener(this);
				menus[i].add(menuItems.get(menuItems.size() - 1));
			}
		}
	}

	public JMenuBar getJMenuBar() {
		return menuBar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Component> components = new ArrayList<Component>();
		JMenuItem m = (JMenuItem) e.getSource();
		DatePicker startDatePicker;
		DatePicker endDatePicker;
		
		JButton searchBtn = new JButton("조회");
		JButton addBtn = new JButton("추가");
		JButton deleteBtn = new JButton("삭제");
		
		Table table;
		String title=null;
		String header[];
		String[][] contents;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		switch (m.getText()) {
		case "메뉴 조회":
			header = new String[3];
			header[0] = "메뉴일련번호";
			header[1] = "메뉴명";
			header[2] = "가격";
			contents = new String[0][3];
			table = new Table(contents, header);

			ProductManager productManager = ProductManager.getInstance();
			List<Product> productList = productManager.getList();

			for (Product p : productList) {
				Vector<String> row = new Vector<String>();
				row.addElement(p.getCode());
				row.addElement(p.getName());
				row.addElement(Integer.toString(p.getPrice()));
				table.getDtm().addRow(row);
			}

			JTextField codeField = new JTextField();
			JTextField menuName = new JTextField();
			JTextField price = new JTextField();

			addBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (codeField == null || menuName == null || price == null) {
						return;
					}

					String inputStr[] = new String[3];
					inputStr[0] = codeField.getText();
					inputStr[1] = menuName.getText();
					inputStr[2] = price.getText();

					Product p = new Product();
					p.setCode(codeField.getText());
					p.setName(menuName.getText());
					try {
						p.setPrice(Integer.parseInt(price.getText()));
					} catch (Exception exception) {
						return;
					}
					productManager.add(p);

					table.getDtm().addRow(inputStr);

					codeField.setText("");
					menuName.setText("");
					price.setText("");
				}
			});

			deleteBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (table.getTable().getSelectedRow() == -1) {
						return;
					} else {
						Object o = table.getDtm().getValueAt(table.getTable().getSelectedRow(), 0);
						table.getDtm().removeRow(table.getTable().getSelectedRow());
						productManager.delete(o.toString());
					}
				}
			});

			panel.add(codeField);
			panel.add(menuName);
			panel.add(price);
			panel.add(addBtn);
			panel.add(deleteBtn);

			components.add(table.getScrollPane());
			components.add(panel);

			title = "메뉴조회";
			break;
		case "주문 조회":
			startDatePicker = new DatePicker();
			endDatePicker = new DatePicker();
			header = new String[5];
			header[0] = "주문일련번호";
			header[1] = "주문시간";
			header[2] = "손님 전화번호";
			header[3] = "결제수단";
			header[4] = "총 주문금액";
			contents = new String[0][5];
			table = new Table(contents, header);

			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UtilDateModel model = startDatePicker.getModel();
					Date date = model.getValue();
					if (date == null)
						return;
					LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					model = endDatePicker.getModel();
					date = model.getValue();
					if (date == null)
						return;
					LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					OrderController orderController = OrderController.getInstance();
					List<Order> orderList = orderController.getOrderList(startDate, endDate, false);
					table.getDtm().setNumRows(0);

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
							continue;
						}
						row.addElement(Integer.toString(o.getTotalPrice()));
						table.getDtm().addRow(row);
					}
				}
			});
			
			panel.add(startDatePicker.getJDatePicker());
			panel.add(endDatePicker.getJDatePicker());
			panel.add(searchBtn);
			components.add(panel);
			components.add(table.getScrollPane());

			title = "주문조회";
			break;
		case "주문 추가":
			Kiosk kiosk = new Kiosk();
			components = kiosk.getComponents();
			break;
		case "주문 플랫폼별 조회":
			header = new String[5];
			header[0] = "주문일련번호";
			header[1] = "주문시간";
			header[2] = "손님 전화번호";
			header[3] = "결제수단";
			header[4] = "총 주문금액";
			contents = new String[0][5];
			table = new Table(contents, header);

			JButton baeminBtn = new JButton("배달의 민족");
			baeminBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OrderController orderController = OrderController.getInstance();
					List<BaeminOrder> orderList = orderController.getBaeminOrderList(false);
					table.getDtm().setNumRows(0);

					for (BaeminOrder o : orderList) {
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
							continue;
						}
						row.addElement(Integer.toString(o.getTotalPrice()));
						table.getDtm().addRow(row);
					}
				}
			});
			JButton eatsBtn = new JButton("쿠팡 이츠");
			eatsBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OrderController orderController = OrderController.getInstance();
					List<EatsOrder> orderList = orderController.getEatsOrderList(false);
					table.getDtm().setNumRows(0);

					for (EatsOrder o : orderList) {
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
							continue;
						}
						row.addElement(Integer.toString(o.getTotalPrice()));
						table.getDtm().addRow(row);
					}
				}
			});
			JButton yogiyoBtn = new JButton("요기요");
			yogiyoBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					OrderController orderController = OrderController.getInstance();
					List<YogiyoOrder> orderList = orderController.getYogiyoOrderList(false);
					table.getDtm().setNumRows(0);

					for (YogiyoOrder o : orderList) {
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
							continue;
						}
						row.addElement(Integer.toString(o.getTotalPrice()));
						table.getDtm().addRow(row);
					}
				}
			});
			
			panel.add(baeminBtn);
			panel.add(eatsBtn);
			panel.add(yogiyoBtn);
			components.add(panel);
			components.add(table.getScrollPane());

			title = "주문 플랫폼별 조회";
			break;
		case "날짜별 통계":
			startDatePicker = new DatePicker();
			endDatePicker = new DatePicker();
			Graph graph = new Graph();

			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UtilDateModel model = startDatePicker.getModel();
					Date date = model.getValue();
					if (date == null)
						return;
					LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					model = endDatePicker.getModel();
					date = model.getValue();
					if (date == null)
						return;
					LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					OrderController orderController = OrderController.getInstance();
					LinkedHashMap<String, Integer> orderList = orderController.getSalesStaticByDate(startDate, endDate);

					graph.addData(orderList);
				}
			});
			
			panel.add(startDatePicker.getJDatePicker());
			panel.add(endDatePicker.getJDatePicker());
			panel.add(searchBtn);
			components.add(panel);
			components.add(graph.getChartPanel());

			title = "날짜별통계";
			break;
		case "시간대별 통계":
			startDatePicker = new DatePicker();
			endDatePicker = new DatePicker();
			graph = new Graph();

			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UtilDateModel model = startDatePicker.getModel();
					Date date = model.getValue();
					if (date == null)
						return;
					LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					model = endDatePicker.getModel();
					date = model.getValue();
					if (date == null)
						return;
					LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					OrderController orderController = OrderController.getInstance();
					LinkedHashMap<String, Integer> orderList = orderController.getSalesStaticByHour(startDate, endDate);

					graph.addData(orderList);
				}
			});
			
			panel.add(startDatePicker.getJDatePicker());
			panel.add(endDatePicker.getJDatePicker());
			panel.add(searchBtn);
			components.add(panel);
			components.add(graph.getChartPanel());

			title = "시간대별통계";
			break;
		case "메뉴별 통계":
			startDatePicker = new DatePicker();
			endDatePicker = new DatePicker();
			graph = new Graph();

			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UtilDateModel model = startDatePicker.getModel();
					Date date = model.getValue();
					if (date == null)
						return;
					LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					model = endDatePicker.getModel();
					date = model.getValue();
					if (date == null)
						return;
					LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					OrderController orderController = OrderController.getInstance();
					LinkedHashMap<String, Integer> orderList = orderController.getSalesStaticByProduct(startDate, endDate);

					graph.addData(orderList);
				}
			});
			
			panel.add(startDatePicker.getJDatePicker());
			panel.add(endDatePicker.getJDatePicker());
			panel.add(searchBtn);
			components.add(panel);
			components.add(graph.getChartPanel());

			title = "메뉴별통계";
			break;
		case "손님별 통계":
			startDatePicker = new DatePicker();
			endDatePicker = new DatePicker();
			graph = new Graph();

			searchBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					UtilDateModel model = startDatePicker.getModel();
					Date date = model.getValue();
					if (date == null)
						return;
					LocalDate startDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					model = endDatePicker.getModel();
					date = model.getValue();
					if (date == null)
						return;
					LocalDate endDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					OrderController orderController = OrderController.getInstance();
					LinkedHashMap<String, Integer> orderList = orderController.getSalesStaticByCustomer(startDate, endDate);

					graph.addData(orderList);
				}
			});
			
			panel.add(startDatePicker.getJDatePicker());
			panel.add(endDatePicker.getJDatePicker());
			panel.add(searchBtn);
			components.add(panel);
			components.add(graph.getChartPanel());

			title = "손님별통계";
			break;
		case "손님 조회":
			header = new String[3];
			header[0] = "손님일련번호";
			header[1] = "이름";
			header[2] = "전화번호";
			contents = new String[0][3];

			table = new Table(contents, header);

			CustomerManager customerManager = CustomerManager.getInstance();
			List<Customer> customerList = customerManager.getList();

			for (Customer c : customerList) {
				Vector<String> row = new Vector<String>();
				row.addElement(c.getCode());
				row.addElement(c.getName());
				row.addElement(c.getPhoneNumber());
				table.getDtm().addRow(row);
			}

			JTextField code = new JTextField(1);
			JTextField customerName = new JTextField();
			JTextField phoneNumber = new JTextField();
			code.setBounds(50, 70, 200, 40);

			addBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (code == null || customerName == null || phoneNumber == null) {
						return;
					}

					String inputStr[] = new String[3];
					inputStr[0] = code.getText();
					inputStr[1] = customerName.getText();
					inputStr[2] = phoneNumber.getText();

					Customer c = new Customer();
					c.setCode(code.getText());
					c.setName(customerName.getText());
					c.setPhoneNumber(phoneNumber.getText());
					customerManager.add(c);

					table.getDtm().addRow(inputStr);

					code.setText("");
					customerName.setText("");
					phoneNumber.setText("");
				}
			});

			deleteBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (table.getTable().getSelectedRow() == -1) {
						return;
					} else {
						Object o = table.getDtm().getValueAt(table.getTable().getSelectedRow(), 0);
						table.getDtm().removeRow(table.getTable().getSelectedRow());
						customerManager.delete(o.toString());
					}
				}
			});

			panel.add(code);
			panel.add(customerName);
			panel.add(phoneNumber);
			panel.add(addBtn);
			panel.add(deleteBtn);

			components.add(table.getScrollPane());
			components.add(panel);
			title = "손님조회";
			break;
		default:
			return;
		}
		GUIMain.frame.addInternalFrame(title, components);
	}
}
