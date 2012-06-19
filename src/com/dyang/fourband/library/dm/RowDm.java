package com.dyang.fourband.library.dm;

public class RowDm {
	private String label;
	private int colorInt;
	private double resisInt;

	public RowDm(String label, int colorInt, double resisInt){
		this.setLabel(label);
		this.setColorInt(colorInt);
		this.setResisInt(resisInt);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setColorInt(int colorInt) {
		this.colorInt = colorInt;
	}

	public int getColorInt() {
		return colorInt;
	}

	public void setResisInt(double resisInt) {
		this.resisInt = resisInt;
	}

	public double getResisInt() {
		return resisInt;
	}

}