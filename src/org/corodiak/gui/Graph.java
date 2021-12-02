package org.corodiak.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Font;
import java.util.LinkedHashMap;

public class Graph {
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private DefaultCategoryDataset dataset;

	public Graph() {
		dataset = new DefaultCategoryDataset();
		chart = ChartFactory.createBarChart("���", "����", "��ġ", dataset);
		chart.getTitle().setFont(new Font("����", Font.BOLD, 15));
		chart.getCategoryPlot().getDomainAxis().setLabelFont(new Font("����", Font.BOLD, 13));
		chart.getCategoryPlot().getDomainAxis().setTickLabelFont(new Font("����", Font.BOLD, 8));
		chart.getCategoryPlot().getRangeAxis().setLabelFont(new Font("����", Font.BOLD, 13));
		chart.getCategoryPlot().getRangeAxis().setTickLabelFont(new Font("����", Font.BOLD, 8));
		chartPanel = new ChartPanel(chart);
	}

	public void addData(LinkedHashMap<String, Integer> orderList) {
		orderList.forEach((key, value) -> {
			dataset.addValue(value, "", key);
		});
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}
}
