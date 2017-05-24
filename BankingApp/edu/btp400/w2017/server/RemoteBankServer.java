package edu.btp400.w2017.server;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import com.java.accounts.*;
import edu.btp400.w2017.common.RemoteBank;

public class RemoteBankServer{
	public static void main(String[] args) {
		try
		{
			System.out.println( "Starting the bank server" );
			RemoteBankImpl bankObj = new RemoteBankImpl("Central");
			java.rmi.registry.Registry bankReg = java.rmi.registry.LocateRegistry.createRegistry(5678);
			loadBank(bankObj);
			bankReg.rebind("Central", bankObj);
		}//End of try block
	
		catch( Exception e ) 
		{
	        System.out.println("Error: "+ e);
		}//End of catch block
		System.out.println("Bank server is awaiting connection from clients...");
	  }//End of main method
	
	public static void loadBank(RemoteBank bank) throws RemoteException
	{
		//John Doe's accounts
		GIC gic1 = new GIC("Doe, John", "G0155", 6000, 2, 1.50);
		Chequing chq1 = new Chequing("Doe, John", "M5034", 600, 3.5, 7);
		Savings sac1 = new Savings("Doe, John", "W0398", 250.34, .15);
		//Add John Doe's accounts to the specified bank
		bank.addAccount(gic1);
		bank.addAccount(chq1);
		bank.addAccount(sac1);
		//Mary Ryan's accounts
		GIC gic2 = new GIC("Ryan, Mary", "G0154", 15000, 4, 2.50);
		Chequing chq2 = new Chequing("Ryan, Mary", "M5031", 600, 3.5, 7);
		Savings sac2 = new Savings("Ryan, Mary", "W0397", 250.34, .25);
		//Add Mary Ryan's accounts to the specified bank
		bank.addAccount(gic2);
		bank.addAccount(chq2);
		bank.addAccount(sac2);
	}
}
