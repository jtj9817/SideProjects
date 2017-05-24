package com.java.accounts;
/**

 * @author Joshua Jadulco
 * @created January 19 2017
 * @modified March 2 2017
 */

import java.io.Serializable;
import java.math.*;
import java.util.*;
import java.text.*;

public class Account implements Serializable{
	protected String fullName;
	protected String accNum;
	protected String firstName;
	protected String lastName;
	protected String[] concatName = new String[2];
	protected BigDecimal accBal = new BigDecimal(0.00);
	
	/**
	 * <p>The constructors below are the default and customized ones respectively. The default constructor initializes
	 * the variables of Account class to a safe state while the custom constructor takes in 3 parameters.
	 */
	public Account()
	{
		this("", "", 0.00);
	}
	/**
	 * @param name sets the fullName variable to name, furthermore split into firstName and lastName variables 
	 * @param accn sets the accNum variable
	 * @param bal sets the accBal variable
	 */
	public Account(String name, String accn, double bal)
	{
		//Special case if the Account object to be initialized is given a parameter that's less than 0
		//Account cannot have a negative balance, therefore initialize balance to 0.00
		if(bal < 0.00)
			bal = 0.00;
		accNum = accn;
		accBal = new BigDecimal(bal);
		if(name != null)
		{
			/*Note that the concatName string does not need to concatenate strings but rather it's a string array
			that utilizes the split() method to split the name given by the user into two strings which are then
			assigned to the lastName and firstName variables consecutively.*/
			if(name.matches("([A-Za-z]+),\\s+([A-Za-z]+)"))
			{
				fullName = name;
				concatName = fullName.split(",");
				if(concatName.length > 0)
				{
					 lastName = concatName[0];
					 firstName = concatName[1];
				}
			}
			else
			{
				invokeDefault();
			}
		}
		else
		{
			invokeDefault();
		}
	}
	
	  /**
	   * A method that would set the fullName, firstName, and lastName of an object to empty string once it's called.
	   * @return No return value.
	   */
	public void invokeDefault()
	{
		fullName = "";
		lastName = "";
		firstName = "";
	}
	
	/**
	 * A method that would calculate the hashCode() of an Account object
	 * @return Returns the value of the hashCode() of an Account object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accBal == null) ? 0 : accBal.hashCode());
		result = prime * result + ((accNum == null) ? 0 : accNum.hashCode());
		result = prime * result + Arrays.hashCode(concatName);
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	
	/**
	* @param obj takes in a reference object and compares it to the current object, i.e. acc1.equals(acc2)
	 * @return boolean result returns false by default; if the target object is similar to the current object, result returns true
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Account)
		{
			Account temp = (Account)obj;
			if ((temp.fullName.equals(fullName)) &&
					(temp.accNum.equals(accNum)) &&
					(temp.accBal == accBal) )
					result = true;
		}
		return result;
	}
	
	/**
	 * 
	 * @return returns the fullName attribute of the Account object if it's not null, null is returned when fullName is null
	 */
	public String getFullName() {
		if(fullName != null)
		{
			return fullName.trim();
		}
		else 
			return null;
	}
	/**
	 * 
	 * @param fullName sets the fullName variable 
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	/**
	 * 
	 * @return returns the account number attribute of the Account object
	 */
	public String getAccountNumber() {
		return accNum;
	}
	
	/**
	 * 
	 * @param accNum sets the accNum property
	 */
	public void setAccNum(String accNum) {
		this.accNum = accNum;
	}
	/**
	 * 
	 * @return returns the account balance attribute of the Account object
	 */
	public double getBalance() {
		return accBal.doubleValue();
	}
	/**
	 * 
	 * @param accBal sets the accBal variable of the Account object
	 */
	public void setAccBal(double accBal) {
		this.accBal = new BigDecimal(accBal);
	}
	/**
	 * @return prints a customized display of the Account object
	 */
	@Override
	public String toString() {
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer usrmny = new StringBuffer (money.format(this.accBal.doubleValue()));
		if(fullName == "")
		{
			StringBuffer accdetails = new StringBuffer("\nName: ");
			accdetails.append("User didn't follow name format");
			accdetails.append("\nAccount Number: ");
			accdetails.append(accNum);
			accdetails.append("\nAccount Balance: ");
			accdetails.append(usrmny);
			return accdetails.toString();
		}
		StringBuffer accdetails = new StringBuffer("\nName: ");
		accdetails.append(fullName);
		accdetails.append("\nAccount Number: ");
		accdetails.append(accNum);
		accdetails.append("\nAccount Balance: ");
		accdetails.append(usrmny);
		return accdetails.toString();
	}
	/**
	 * 
	 * @return returns firstName of the Account object
	 */
	public String getFirstName() {
		if(firstName != null)
		{
			return firstName.trim();
		}
		else 
			return "First Name is null";
	}
	/**
	 * 
	 * @param firstName sets the firstName variable of the Account object
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * 
	 * @return returns the last name attribute of the Account object
	 */
	public String getLastName() {
		if(lastName != null)
		{
			return lastName.trim();
		}
		else 
			return "Last name is null";
	}
	/**
	 * 
	 * @param lastName sets the lastName variable of the Account object
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public boolean withdraw(double amount)
	{
		BigDecimal amnt = new BigDecimal(amount);
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer amnt1 = new StringBuffer(money.format(amnt.doubleValue()));
		if(amount >= 0.00)
		{
			if(accBal.compareTo(amnt) == -1)
			{
				System.out.println("Not enough balance to complete the transaction!\n");
				return false;
			}
			else
			{
				accBal = accBal.subtract(amnt);
				System.out.println("You have successfully withdrawn " + amnt1);
				System.out.println("Your current balance is " + money.format(getBalance()));
				return true;
			}

		}
		else
		{
			System.out.println("You entered an invalid amount!\n");
			return false;
		}

	}
	
	public void deposit(double amount){
		BigDecimal amnt = new BigDecimal(amount);
		NumberFormat money = NumberFormat.getCurrencyInstance(Locale.CANADA);
		StringBuffer amnt1 = new StringBuffer(money.format(amnt.doubleValue()));
		if(amount >= 0.00)
		{
			accBal = accBal.add(amnt);
			System.out.println("You have successfully deposited " + amnt1);
			System.out.println("Your current balance is " + money.format(getBalance()) + "\n");
			
		}
		else 
			System.out.println("You entered an invalid amount!\n");		
	}
}
