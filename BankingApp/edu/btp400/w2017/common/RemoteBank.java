package edu.btp400.w2017.common;
import com.java.accounts.*;

import java.io.Serializable;
import java.rmi.*;

public interface RemoteBank extends Remote, Serializable {	
	public Account removeAccount(String accnum) throws RemoteException;
	public Account[] searchByBal(double bal) throws RemoteException; 
	public boolean addAccount(Account obj) throws RemoteException; 
	public String getBankName() throws RemoteException; 
	public Account [] searchByAccountName(String accountName ) throws RemoteException; 
} 
