package au.com.openbiz.trading.logic.manager.chart.impl;

public enum GoogleChartType {

	PIE_3D ("p3");
	
	private String name;
	
	private GoogleChartType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
