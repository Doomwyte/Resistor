package com.dyang.fourband.library.dm;

public class SdDm {
	private String firstBand;
	private String secondBand;
	private String thirdBand;
	private String multiplierBand;
	private String toleranceBand;
	private String resistValue;

	public SdDm(String firstBand, String secondBand, String thirdBand, String multiplierBand, String toleranceBand, String resistValue){
		this.setFirstBand(firstBand);
		this.setSecondBand(secondBand);
		this.setThirdBand(thirdBand);
		this.setMultiplierBand(multiplierBand);
		this.setToleranceBand(toleranceBand);
		this.setResistValue(resistValue);
	}
	public SdDm(String firstBand, String secondBand, String multiplierBand, String toleranceBand, String resistValue){
		this.setFirstBand(firstBand);
		this.setSecondBand(secondBand);
		this.setMultiplierBand(multiplierBand);
		this.setToleranceBand(toleranceBand);
		this.setResistValue(resistValue);
	}
	
	public String getFirstBand() {
		return firstBand;
	}
	public void setFirstBand(String firstBand) {
		this.firstBand = firstBand;
	}
	public String getSecondBand() {
		return secondBand;
	}
	public void setSecondBand(String secondBand) {
		this.secondBand = secondBand;
	}
	public String getThirdBand() {
		return thirdBand;
	}
	public void setThirdBand(String thirdBand) {
		this.thirdBand = thirdBand;
	}
	public String getMultiplierBand() {
		return multiplierBand;
	}
	public void setMultiplierBand(String multiplierBand) {
		this.multiplierBand = multiplierBand;
	}
	public String getToleranceBand() {
		return toleranceBand;
	}
	public void setToleranceBand(String toleranceBand) {
		this.toleranceBand = toleranceBand;
	}
	public String getResistValue() {
		return resistValue;
	}
	public void setResistValue(String resistValue) {
		this.resistValue = resistValue;
	}
}
