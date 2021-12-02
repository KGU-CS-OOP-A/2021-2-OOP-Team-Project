package org.corodiak.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

public class Frame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3394318084593934679L;
	private JFrame mainFrame;
	private JDesktopPane desktopPane = new JDesktopPane();
	private JInternalFrame internalFrame;

	public Frame() {
		mainFrame = new JFrame("주문 관리 프로그램");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(1000, 800);
		mainFrame.setVisible(true);
		mainFrame.add(desktopPane);
	}

	public void addInternalFrame(String title, Component component) {
		JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
		internalFrame.add(component);
		internalFrame.setSize(500, 500);
		internalFrame.setVisible(true);
		desktopPane.add(internalFrame);
		internalFrame.moveToFront();
		revalidate();
		repaint();
		this.internalFrame = internalFrame;
	}

	public void addInternalFrame(String title, ArrayList<Component> components) {
		JInternalFrame internalFrame = new JInternalFrame(title, true, true, true, true);
		internalFrame.setLayout(new BoxLayout(internalFrame.getContentPane(), BoxLayout.Y_AXIS));

		for (Component c : components) {
			internalFrame.add(c);
		}

		internalFrame.setSize(400, 300);
		internalFrame.setVisible(true);
		desktopPane.add(internalFrame);
		internalFrame.moveToFront();
		revalidate();
		repaint();
		this.internalFrame = internalFrame;
	}

	public void setMenuBar(MenuBar menuBar) {
		mainFrame.setJMenuBar(menuBar.getJMenuBar());
	}

	public void addComponent(Object component) {
		mainFrame.add((Component) component);
	}

	public JInternalFrame getInternalFrame() {
		return internalFrame;
	}
}
