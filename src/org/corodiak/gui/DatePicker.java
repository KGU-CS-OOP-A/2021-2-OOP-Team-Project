package org.corodiak.gui;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class DatePicker {
	UtilDateModel model;
	JDatePanelImpl datePanel;
	private JDatePickerImpl datePicker;

	public DatePicker() {
		model = new UtilDateModel();
		datePanel = new JDatePanelImpl(model);
		datePicker = new JDatePickerImpl(datePanel);
	}

	public UtilDateModel getModel() {
		return model;
	}

	public JDatePickerImpl getJDatePicker() {
		return datePicker;
	}
}
