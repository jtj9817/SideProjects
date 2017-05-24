package edu.btp400.w2017.server;

import com.java.accounts.*;
import edu.btp400.w2017.common.RemoteBank;
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.Objects;

public class RemoteBankImpl extends UnicastRemoteObject  implements RemoteBank  {
	private String bankName;
	private ArrayList<Account> accounts;
	
	//Default constructor for RemoteBankImpl
	public RemoteBankImpl() throws RemoteException
	{
		bankName = "No bank Name";
		accounts = new ArrayList<Account>();
	}
	
	//Overloaded constructor for RemoteBankImpl
	public RemoteBankImpl(String bankName) throws RemoteException
	{
		this.bankName = bankName;
		accounts = new ArrayList<Account>();
	}


	public Account removeAccount(String accnum) throws RemoteException
	{
		Account acc = null;
		for(int i=0; i < accounts.size(); i++)
		{
			if(accounts.get(i).getAccountNumber().equals(accnum))
			{
				acc = accounts.get(i);
				accounts.remove(i);
				return acc;
			}
		}
		return acc;
	}
	public Account[] searchByBal(double bal) throws RemoteException
	{
		int counter = 0;
		Account accSameBal[];
		for(int i =0; i < accounts.size(); i++)
		{
			Account acc = accounts.get(i);
			if(acc.getBalance() == bal)
			{
				counter++;
			}
		}
		accSameBal = new Account[counter];
		for(int i =0; i < accounts.size(); i++)
		{
			Account acc = accounts.get(i);
			if((double)acc.getBalance() == bal)
			{
				accSameBal[i] = accounts.get(i);
			}
		}
		return accounts.toArray(accSameBal);
	}
	public boolean addAccount(Account obj) throws RemoteException 
	{
		  boolean done = true;	
		  //Preliminary conditional statements to flag if the Account object doesn't follow the set rules
	   	   if (obj == null)
	   	   {
	   		   done = false;
	   	   }
	   	   else
	   	   {
	 		  for(int x = 0; x < accounts.size(); x++)
			  {
				  if(obj.equals(accounts.get(x)))
				  {
					  done = false;
				  }
			  }
	   	   }
	   	   //At this point, the Account object is guaranteed to have met the standards
	   	   //Therefore, the Account object should be put in the Bank object's array of Account objects
	   	   if(done == true)
	   	   {
	   		   accounts.add(obj);
	   	   }
	   	   return done;
	}
	public String getBankName() throws RemoteException
	{
		return bankName;
	}
	public Account [] searchByAccountName(String accountName ) throws RemoteException
	{
		int counter = 0;
		int as = accounts.size();
		int j = 0;
		Account tempArray[];
		for(int i =0; i < as; i++)
		{
			if(accounts.get(i).getFullName().equals(accountName))
			{
				counter++;
			}
		}
		tempArray = new Account[counter];
		for(int i =0; i < as; i++)
		{
			if(accounts.get(i).getFullName().equals(accountName))
			{
				tempArray[j] = accounts.get(i);
				j++;
			}
		}
		return tempArray;
	}
	
	@Override
	//Improved toString method from Lab2, now appends an account detail instead of calling the printAccounts method
	public String toString() {
		StringBuffer bankDetails = new StringBuffer();
		bankDetails.append("\nBank Name: ");
		bankDetails.append(this.bankName);
		bankDetails.append("\nNumber of accounts opened: ");
		bankDetails.append(this.accounts.size());
		return bankDetails.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		//Checks if the current object has the same reference value as the compared object
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		//Checks if the compared object is the same type(class) as the current object
		if (getClass() != obj.getClass())
			return false;
		//Uses type-casting on the compared object, therefore assuming that the following operations will
		//be permissible on the compared object
		RemoteBankImpl other = (RemoteBankImpl) obj;
		//Checks if the accounts of the compared object has the same values as the current object's
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		return true;
	}
}
