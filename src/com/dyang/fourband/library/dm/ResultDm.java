package com.dyang.fourband.library.dm;

public class ResultDm {
	private int firstBand;
	private int secondBand;
	private int thirdBand;
	private double multiplierBand;
	private double toleranceBand;
	private double resisVal;
	public ResultDm(int firstBand, int secondBand, int thirdBand, double multiplierBand, double toleranceBand, double resisVal){
		this.setFirstBand(firstBand);
		this.setSecondBand(secondBand);
		this.setThirdBand(thirdBand);
		this.setMultiplierBand(multiplierBand);
		this.setToleranceBand(toleranceBand);
		this.setResisVal(resisVal);
	}
	public ResultDm(int firstBand, int secondBand, double multiplierBand, double toleranceBand, double resisVal){
		this.setFirstBand(firstBand);
		this.setSecondBand(secondBand);
		this.setMultiplierBand(multiplierBand);
		this.setToleranceBand(toleranceBand);
		this.setResisVal(resisVal);
	}
	public void setFirstBand(int firstBand) {
		this.firstBand = firstBand;
	}
	public int getFirstBand() {
		return firstBand;
	}
	public void setSecondBand(int secondBand) {
		this.secondBand = secondBand;
	}
	public int getSecondBand() {
		return secondBand;
	}
	public void setThirdBand(int thirdBand) {
		this.thirdBand = thirdBand;
	}
	public int getThirdBand() {
		return thirdBand;
	}
	public void setToleranceBand(double toleranceBand) {
		this.toleranceBand = toleranceBand;
	}
	public double getToleranceBand() {
		return toleranceBand;
	}
	public void setMultiplierBand(double multiplierBand) {
		this.multiplierBand = multiplierBand;
	}
	public double getMultiplierBand() {
		return multiplierBand;
	}
	public double getResisVal() {
		return resisVal;
	}
	public void setResisVal(double resisVal) {
		this.resisVal = resisVal;
	}
}
