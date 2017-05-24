package com.java.accounts;

import java.io.Serializable;
import java.math.*;
import java.util.*;
import java.text.*;

public class Chequing extends Account implements Serializable{
	private double serviceCharges;
	private double baseSvcCharge;
	private int maxTransactions;
	private int countTransactions;
	double[] Transactions;
	
	/**
	 * @Note The default constructor for the Chequing account object. Sets service charge fees to $.25 and
	 * number of maximum transactions to 3. Also initializes the svcChargeCounter to 0.
	 */
	public Chequing(){
		super();
		this.serviceCharges = .25;
		this.maxTransactions = 3;
		this.baseSvcCharge = this.serviceCharges;
		this.countTransactions = 0;
		Transactions = new double[this.maxTransactions];
	}
	
	public Chequing(String fullName, String accnum, double startBal, double svcCharge, int transacLimit){
		super(fullName, accnum, startBal);
		this.serviceCharges = svcCharge;
		this.maxTransactions = transacLimit;
		this.baseSvcCharge = this.serviceCharges;
		this.countTransactions = 0;
		Transactions = new double[this.maxTransactions];
	}

	/**
	 * @return returns the value of the hash code of a Chequing object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxTransactions;
		long temp;
		temp = Double.doubleToLongBits(serviceCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/**
	 * @return Returns true if both the Chequing account objects are the same, otherwise false
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chequing)
		{
			Chequing temp = (Chequing)obj;
			if ((temp.fullName.equals(fullName)) &&
				(temp.accNum.equals(accNum)) &&
				(temp.accBal == accBal) &&
				Double.doubleToLongBits(serviceCharges) == Double.doubleToLongBits(temp.serviceCharges) 
				&& maxTransactions == temp.maxTransactions)
					return true;
		}
		return false;
	}
	
	/**
	 * @return returns a formatted output of a Chequing object's account details
	 */
	@Override
	public String toString() {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer acctDetails = new StringBuffer();
		acctDetails.append(super.toString());
		acctDetails.append("\nType: CHQ");
		acctDetails.append("\nService charge: ");
		acctDetails.append(money.format(this.getBaseSvcCharge()));
		acctDetails.append("\nTotal Service Charges: ");
		acctDetails.append(money.format(getServiceCharges()));
		acctDetails.append("\nNumber of Transactions Allowed: ");
		acctDetails.append(this.getMaxTransactions());
		acctDetails.append("\nList of Transactions: ");
		acctDetails.append(this.printTransactions());
		acctDetails.append("\nFinal Balance: ");
		acctDetails.append(this.getBalance());
		return acctDetails.toString();
	}

	/**
	 * 
	 * @return returns the total service charges. Additional service charges are added if there are any
	 * transactions committed by calling the addServiceCharges() method
	 */
	public double getServiceCharges() {
		return serviceCharges;
	}

	/**
	 * @return returns the maximum amount of transactions that can be done
	 */
	public int getMaxTransactions() {
		return maxTransactions;
	}

	/**
	 * @param maxTransactions modifies the maxTransactions data field if needed
	 */
	public void setMaxTransactions(int maxTransactions) {
		this.maxTransactions = maxTransactions;
	}
	
	/**
	 * @return Returns the baseSvcCharge data field
	 */
	public double getBaseSvcCharge() {
		return baseSvcCharge;
	}
	/**
	 * @param baseSvcCharge sets the baseSvcCharge data field to this value
	 * @Note baseSvcCharge is a separate data field to retain the original service charge fee
	 *that the Chequing object was initialized with.
	 */
	public void setBaseSvcCharge(double baseSvcCharge) {
		this.baseSvcCharge = baseSvcCharge;
	}
	/**
	 * 
	 * @return returns the number of transactions that has been done 
	 */
	public int getCountTransactions() {
		return countTransactions;
	}
	
	/**
	 * @param countTransactions sets the number of transactions to this amount
	 */
	public void setCountTransactions(int countTransactions) {
		this.countTransactions = countTransactions;
	}
	/**
	 * 
	 * @param transacValue The transaction of the value that will be added
	 * to the Transactions array. 
	 */
	public void addTransaction(double transacValue, String flag){
		  if(flag.trim().compareTo("Withdraw") == 0)
		  {
			  transacValue = transacValue * -1;
		  }
		  else
		  {
			  transacValue = transacValue * 1;
		  }
		  if  (this.getCountTransactions() > this.getMaxTransactions()){
			  System.out.println("Can't add the transaction! Exceeded maximum transactions allowed!");
		  }
	   	  else
	   	  {
	   		  Transactions[this.getCountTransactions()] = transacValue;
	   		  this.countTransactions++;
	   	   }
	}
	/**
	 * 
	 * @Purpose Always called when a valid withdraw() or deposit() method
	 * is called. Adds service charges based on the base service charge
	 * that was initialized during a Chequing account object's creation.
	 */
	public void addServiceCharges(){
		this.serviceCharges += this.getBaseSvcCharge();
	}
	/**
	 * @param amoun The amount to be deducted from the account's balance. Must be a positive amount.
	 * @Note Even though the parameter received is positive, it is stored in the Transactions[] array as a
	 * negative value using the "Withdraw" flag.
	 * @return returns true if the withdrawal transaction is valid, false otherwise.
	 */
	@Override
	public boolean withdraw(double amount){
		BigDecimal amnt = new BigDecimal(amount);
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer amnt1 = new StringBuffer(money.format(amnt.doubleValue()));
		if(amount > 0.00)
		{
			if(this.getCountTransactions() > this.getMaxTransactions())
			{
				System.out.println("\nCan't perform the transaction! Maximum transactions has been reached!\n");
				return false;
			}
			if(accBal.compareTo(amnt) == -1)
			{
				System.out.println("\nNot enough balance to complete the transaction!\n");
				return false;
			}
			else
			{
				accBal = accBal.subtract(amnt);
				this.addTransaction(amount, "Withdraw");
				this.addServiceCharges();
				System.out.println("You have successfully withdrawn " + amnt1);
				System.out.println("Your current balance is " +  money.format(this.accBal.doubleValue()));
				return true;
			}
		}
		else
		{
			System.out.println("You entered an invalid amount!\n");
			return false;
		}
	}
	/**
	 * @param amount The amount to be added to the bank account. Must be positive.
	 */
	@Override
	public void deposit(double amount){
		BigDecimal amnt = new BigDecimal(amount);
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer amnt1 = new StringBuffer(money.format(amnt.doubleValue()));
		if(amount >= 0.00)
		{
			if(this.getCountTransactions() > this.getMaxTransactions())
			{
				System.out.println("\nCan't perform the transaction! Maximum transactions has been reached!\n");
			}
			accBal = accBal.add(amnt);
			this.addTransaction(amount, "Deposit");
			this.addServiceCharges();
			System.out.println("You have successfully deposited " + amnt1);
			System.out.println("Your current balance is " + money.format(this.accBal.doubleValue()) + "\n");
			
		}
		else 
			System.out.println("You entered an invalid amount!\n");		
	}
	/**
	 * 
	 * @return returns the account balance data field of the Chequing object, current balance - 
	 */
	@Override
	public double getBalance() {
		return accBal.doubleValue() - this.serviceCharges;
	}
	
	/**
	 * @note prints the contents of the Transactions[] array in a formatted manner.
	 */
	public String printTransactions(){
		StringBuffer num = new StringBuffer();
		for (int i = 0; i < Transactions.length; i++)
		{
			if(Transactions[i] != 0.0)
			{
				if(Transactions[i] > 0.0)
				{
					num.append("+");
					num.append((double)Transactions[i]);
					num.append(",");
				}
				else
				{
					num.append(Transactions[i]);
					num.append(",");
				}
				/*
				if(i > 0.00)
				{
					System.out.print(",");
				}
				if(Transactions[i] > 0.0)
				{
					System.out.print("+" + Transactions[i]);
					
				}
				else
				{
					System.out.print(Transactions[i]);
				}
				*/
			}
		}
		return num.toString();
	}
}
