package com.java.accounts;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Joshua Jadulco
 * @since March 2, 2017
 *
 */
public class Savings extends Account implements Taxable, Serializable {
	private double interestRate;
	private int taxAmount = 0;
	private double taxRate = 0.15;
	
	/**
	 * @Purpose Default constructor for a Savings object. Uses the Account class' 
	 * default constructor and initializes interestRate to 0.10.
	 */
	public Savings()
	{
		super();
		this.interestRate = 0.10;
	}
	
	/**
	 * @param fullName sets the fullName data field to this value
	 * @param accnum sets the account number to this value
	 * @param startBal sets the account balance to this value
	 * @param itrstrate sets the interest rate to this value
	 */
	public Savings(String fullName, String accnum, double startBal, double itrstrate)
	{
		super(fullName, accnum, startBal);
		this.interestRate = itrstrate;
	}
	
	/**
	 * @Purpose Performs a taxation on the interest income if it's more than $50.00, otherwise no taxation will occur.
	 * @param taxRate sets the taxRate data field to this value(if invoked)
	 */
	public void calculateTax(double taxRate) {
		this.taxRate = taxRate;
		if(this.getInterestIncome() > 50.00)
		{
			this.taxAmount = (int) (this.getInterestIncome() * taxRate);
		}
		
	}
	/**
	 * @return returns the taxAmount that has been calculated by calculateTax() method. Default is $0.00.
	 */
	public double getTaxAmount() {
		this.calculateTax(this.taxRate);
		return this.taxAmount;
	}
	
	/**
	 * @return returns a report containing tax information for a Savings account object.
	 */
	public String createTaxStatement() {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer TaxDetails = new StringBuffer();
		TaxDetails.append("\nTax rate: ");
		TaxDetails.append((int)(this.taxRate * 100));
		TaxDetails.append("%");
		TaxDetails.append("\nAccount Number: ");
		TaxDetails.append(this.getAccountNumber());
		TaxDetails.append("\nInterest Income: ");
		TaxDetails.append(money.format(this.getInterestIncome()));
		TaxDetails.append("\nAmount of tax: ");
		TaxDetails.append(money.format(this.getTaxAmount()));
		return TaxDetails.toString();
	}
	/**
	 * @return Returns the interestRate
	 */
	public double getInterestRate() {
		return this.interestRate;
	}
	/**
	 * @param interestRate is the value of the interest rate that will be set accordingly
	 */
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	/**
	 * @return returns the interest income of an account's balance multiplied by its interest rate
	 * @Note The realrate variable is used to perform the proper conversion from percentages 
	 */
	public double getInterestIncome(){
		double realrate = (interestRate * .01);
		return this.accBal.doubleValue() * realrate;
	}
	/**
	 * @return returns the Savings account object's balance that has been compounded by an income with interest
	 */
	public double getBalance(){
		return this.accBal.doubleValue() + this.getInterestIncome();
	}
	/**
	 * @return returns a hashCode for the Savings account object
	 */
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(interestRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/**
	 * @param Receives an object which should be of type Savings
	 * @return Returns true if both Savings objects are the same (all the account details are the same), returns false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Savings)
		{
			Savings temp = (Savings)obj;
			if ((temp.fullName.equals(fullName)) &&
				(temp.accNum.equals(accNum)) &&
				(temp.accBal == accBal) &&
				 Double.doubleToLongBits(interestRate) == Double.doubleToLongBits(temp.interestRate) &&
				 taxRate == temp.taxRate)
					return true;
		}
		return false;
	}
	/**
	 * @return returns a formatted output of a Savings account object's data fields
	 */
	@Override
	public String toString() {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer accdetails = new StringBuffer("\nType: SAV");
		accdetails.append("\nInterest Rate: ");
		accdetails.append(this.getInterestRate());
		accdetails.append("%");
		accdetails.append("\nInterest Income: ");
		accdetails.append(money.format(this.getInterestIncome()));
		accdetails.append("\nFinal Balance: ");
		accdetails.append(money.format(this.getBalance()));
		return super.toString().concat(accdetails.toString());
	}
}
