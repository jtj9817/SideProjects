package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.text.NumberFormat;
import java.util.Locale;


public class GIC extends Account implements Taxable, Serializable{
	private int invstYears;
	private boolean depositCall = false;
	private double annIntrstRate;
	private double interestIncome;
	private double taxRate = .15;
	private double taxAmount;
	/**
	 * @Note Default constructor for a GIC object. The default constructor of the Account() parent class is used.
	 * Investment years and annual interest rate is set to 1 and 1.25 consecutively.
	 */
	public GIC()
	{
		super();
		this.invstYears = 1;
		this.annIntrstRate = 1.25;
	}
	
	/**
	 * @param name sets the name of the GIC object to this
	 * @param anum sets the account number of the GIC object to this
	 * @param startBal sets the principal amount of the GIC object to this 
	 * @param years sets how many years the investment is going to be
	 * @param rate sets the annual interest rate to this value
	 */
	public GIC(String name, String anum, double startBal, int years, double rate)
	{
		super(name, anum, startBal);
		this.invstYears = years;
		this.annIntrstRate = rate;
	}
	/**
	 * @Note empty method
	 */
	@Override
	public boolean withdraw(double amount){
		return false;
	}
	/**
	 * @Note empty method
	 */
	@Override
	public void deposit(double amount){
		this.depositCall = true;
	}
	
	/**
	 * @return returns the final balance of the GIC account 
	 */
	public double getBalance(){
		//this.maturityBalance = this.accBal.doubleValue() * Math.pow(1.0 + rate, this.invstYears);
		double rate = (this.annIntrstRate * .01);
		double gicRate = Math.pow((1+rate/100), invstYears); 
		double trueBalance = super.getBalance()*gicRate;
		return trueBalance;
	}
	
	/**
	 * @param amount sets the taxRate data field to this value
	 * @note performs the tax calculations on the interest income
	 */
	public  void calculateTax(double amount){
		this.taxRate = amount;
		this.taxAmount = (this.getInterestIncome() * taxRate);
	}
	/**
	 * @return returns a report containing tax information for a GIC account object.
	 */
	public String createTaxStatement(){
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer taxStatement = new StringBuffer();
		taxStatement.append("\nTax rate: ");
		taxStatement.append((int)(this.taxRate * 100));
		taxStatement.append("%");
		taxStatement.append("\nAccount Number: ");
		taxStatement.append(this.getAccountNumber());
		taxStatement.append("\nInterest Income: ");
		taxStatement.append(money.format(this.getInterestIncome()));
		taxStatement.append("\nAmount of tax: ");
		taxStatement.append(money.format(this.getTaxAmount()));
		return taxStatement.toString();
	}
	/**
	 * @note performs the tax calculations and returns the taxAmount 
	 */
	public double getTaxAmount(){
		this.calculateTax(this.taxRate);
		return this.taxAmount;	
	}
	/**
	 * @return returns the hashCode of a GIC object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(annIntrstRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(interestIncome);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + invstYears;
		return result;
	}
	/**
	 * @param obj the GIC object to be compared to
	 * @return returns true if both GIC objects are the same, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GIC)
		{
			GIC temp = (GIC)obj;
			if ((temp.fullName.equals(fullName)) &&
				(temp.accNum.equals(accNum)) &&
				(temp.accBal == accBal) &&
				Double.doubleToLongBits(annIntrstRate) == Double.doubleToLongBits(temp.annIntrstRate) &&
				Double.doubleToLongBits(interestIncome) == Double.doubleToLongBits(temp.interestIncome) &&
						invstYears == temp.invstYears)
					return true;
		}
		return false;
	}
	/**
	 * @return returns a formatted output of a GIC object's account details
	 */
	@Override
	public String toString() {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer accdetails = new StringBuffer("\nType: GIC");
		accdetails.append("\nAnnual Interest Rate: ");
		accdetails.append(this.getAnnIntrstRate());
		accdetails.append("%");
		accdetails.append("\nPeriod of Investment: ");
		accdetails.append(this.getInvstYears());
		accdetails.append(" years");
		accdetails.append("\nInterest Income at maturity: ");
		accdetails.append(money.format(this.getInterestIncome()));
		accdetails.append("\nBalance at Maturity: ");
		accdetails.append(money.format(this.getBalance()));
		return super.toString().concat(accdetails.toString());
	}
	
	/**
	 * @return returns the annual interest rate for the GIC object
	 */
	public double getAnnIntrstRate() {
		return annIntrstRate;
	}
	/**
	 * 
	 * @param annIntrstRate sets the annual interest rate to this value if needed.
	 */
	public void setAnnIntrstRate(double annIntrstRate) {
		this.annIntrstRate = annIntrstRate;
	}
	/**
	 * @return returns the investment years of the GIC object
	 */
	public int getInvstYears() {
		return invstYears;
	}
	/**
	 * 
	 * @param invstYears sets the investment years to this value if needed.
	 */
	public void setInvstYears(int invstYears) {
		this.invstYears = invstYears;
	}
	/**
	 * @return returns the income with interest of the GIC object
	 */
	public double getInterestIncome() {
		return this.interestIncome = this.getBalance() - this.accBal.doubleValue();
	}
	/**
	 * 
	 * @param interestIncome sets the interestIncome to this value if needed.
	 */
	public void setInterestIncome(double interestIncome) {
		this.interestIncome = interestIncome;
	}

	/**
	 * 
	 * @return returns the sate of the depositCall variable. If the deposit() method is called, it should be true.
	 * Otherwise, false.
	 */
	public boolean isDepositCall() {
		return depositCall;
	}
}
