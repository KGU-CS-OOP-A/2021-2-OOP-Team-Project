package org.corodiak.gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class Panel {
	private JPanel panel;

	public Panel(ArrayList<Component> components) {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		for (Component c : components) {
			panel.add(c);
		}
	}

	public JPanel getPanel() {
		return panel;
	}
}
