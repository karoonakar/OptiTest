package com.sg.app.entities;
/**
 * @author Karoonakar
 *
 */
public class DealScf {

	
	private String numtra;
	private String notionalCurrency;
	private String maturity;
	private String rateIndex;
	private String flowFrequency;
	private String flowCurrency;
	private String fixedStrike;
	private String notional;
	private String discountFlag;
	private String varaibleStrike;
	
	
	
	public DealScf(String numtra, String notionalCurrency, String maturity,
			String rateIndex, String flowFrequency, String flowCurrency,
			String fixedStrike, String notional, String discountFlag,
			String varaibleStrike) {
		super();
		this.numtra = numtra;
		this.notionalCurrency = notionalCurrency;
		this.maturity = maturity;
		this.rateIndex = rateIndex;
		this.flowFrequency = flowFrequency;
		this.flowCurrency = flowCurrency;
		this.fixedStrike = fixedStrike;
		this.notional = notional;
		this.discountFlag = discountFlag;
		this.varaibleStrike = varaibleStrike;
	}
	
	
	public String getNumtra() {
		return numtra;
	}
	public void setNumtra(String numtra) {
		this.numtra = numtra;
	}
	public String getNotionalCurrency() {
		return notionalCurrency;
	}
	public void setNotionalCurrency(String notionalCurrency) {
		this.notionalCurrency = notionalCurrency;
	}
	public String getMaturity() {
		return maturity;
	}
	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}
	public String getRateIndex() {
		return rateIndex;
	}
	public void setRateIndex(String rateIndex) {
		this.rateIndex = rateIndex;
	}
	public String getFlowFrequency() {
		return flowFrequency;
	}
	public void setFlowFrequency(String flowFrequency) {
		this.flowFrequency = flowFrequency;
	}
	public String getFlowCurrency() {
		return flowCurrency;
	}
	public void setFlowCurrency(String flowCurrency) {
		this.flowCurrency = flowCurrency;
	}
	public String getFixedStrike() {
		return fixedStrike;
	}
	public void setFixedSTRIKE(String fixedStrike) {
		this.fixedStrike = fixedStrike;
	}
	public String getNotional() {
		return notional;
	}
	public void setNotional(String notional) {
		this.notional = notional;
	}
	public String getDiscountFlag() {
		return discountFlag;
	}
	public void setDiscountFlag(String discountFlag) {
		this.discountFlag = discountFlag;
	}
	public String getVaraibleStrike() {
		return varaibleStrike;
	}
	public void setVaraibleStrike(String varaibleStrike) {
		this.varaibleStrike = varaibleStrike;
	}
	
}
