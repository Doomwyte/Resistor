package com.dyang.fourband.library.dm;

public class UnitDm {
	private String label;
	private double multiple;
	public UnitDm(String label, double multiple){
		this.setLabel(label);
		this.setMultiple(multiple);
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
	public void setMultiple(double multiple) {
		this.multiple = multiple;
	}
	public double getMultiple() {
		return multiple;
	}
}
