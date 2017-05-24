package edu.btp400.w2017.client;
import java.rmi.*;
import java.rmi.server.*;
import java.util.Scanner;
import com.java.accounts.Account;
import com.java.accounts.Chequing;
import com.java.accounts.GIC;
import com.java.accounts.Savings;
import com.java.accounts.Taxable;
import edu.btp400.*;
import edu.btp400.w2017.common.RemoteBank;

public class RemoteBankClient {
	public static Scanner scanner = new Scanner(System.in);
	public static void displayMenu(String bankName)
	{
		StringBuffer welcomeMessage = new StringBuffer();
		welcomeMessage.append("\nWelcome to ");
		welcomeMessage.append(bankName);
		welcomeMessage.append(" Bank!");
		welcomeMessage.append("\n1. Open an account.");
		welcomeMessage.append("\n2. Close an account.");
		welcomeMessage.append("\n3. Deposit money");
		welcomeMessage.append("\n4. Withdraw money.");
		welcomeMessage.append("\n5. Display an account by balance or account number.");
		welcomeMessage.append("\n6. Display a tax statement.");
		welcomeMessage.append("\n7. Exit");
		System.out.println(welcomeMessage.toString());
	}
	public static void openAccount(RemoteBank bank) throws RemoteException
	{
		// BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		Scanner scanner = new Scanner(System.in);
		String [] usrinfoarr;
		System.out.println("\nPlease enter the account type(SAV/CHQ/GIC): ");
		while(scanner.hasNextLine())
		{
			String acctype = scanner.nextLine();
			try{
				if(acctype.trim().isEmpty())
					System.out.println("\nInvalid input! Try again!");
				else
				{
					System.out.println("\nPlease enter the account information at one line");
					System.out.println("Format is: Doe, john; A1234; 1000.00; .25; 10");
					String usrinfo = scanner.nextLine();
						int size = usrinfo.split(";").length;
						usrinfoarr = new String[size];
						usrinfoarr = usrinfo.split(";");
						for(int i=0; i < size; i++)
							usrinfoarr[i] = usrinfoarr[i].trim();
						if(size == 3 )
						{
							double accbal = Double.parseDouble(usrinfoarr[2]);
							Account tempacc = new Account(usrinfoarr[0],usrinfoarr[1], accbal);
							if(bank.addAccount(tempacc))
							{
								System.out.println("Account successfully created!");
								displayMenu(bank.getBankName());
								int usr = menuChoice();
								reloadMenu(usr, bank);
							}
						}
						else if (size > 3 && acctype.trim().equals("SAV"))
						{
							double accbal = Double.parseDouble(usrinfoarr[2]);
							double rate = Double.parseDouble(usrinfoarr[3]);
							Savings tempacc = new Savings(usrinfoarr[0],usrinfoarr[1], accbal, rate);
							if(bank.addAccount(tempacc))
							{
								System.out.println("Account successfully created!");
								displayMenu(bank.getBankName());
								int usr = menuChoice();
								reloadMenu(usr, bank);
							}
						}
						else if (size > 3 && acctype.trim().equals("CHQ"))
						{
							System.out.println("usrinfo array contents :");
							for(int i =0; i < usrinfoarr.length; i++)
								System.out.println(i + " " + usrinfoarr[i].toString());
							double accbal = Double.parseDouble(usrinfoarr[2]);
							double svcCharge = Double.parseDouble(usrinfoarr[3]);
							int transacLimit = Integer.parseInt(usrinfoarr[4]); 
							Chequing tempacc = new Chequing(usrinfoarr[0],usrinfoarr[1], accbal, svcCharge, transacLimit);
							if(bank.addAccount(tempacc))
							{
								System.out.println("Account successfully created!");
								displayMenu(bank.getBankName());
								int usr = menuChoice();
								reloadMenu(usr, bank);
							}
						}
						else if (size > 3 && acctype.trim().equals("GIC"))
						{
							double accbal = Double.parseDouble(usrinfoarr[2]);
							double years = Double.parseDouble(usrinfoarr[4]);
							double rate = Double.parseDouble(usrinfoarr[3]);
							GIC tempacc = new GIC(usrinfoarr[0],usrinfoarr[1], accbal, (int)years, rate);
							if(bank.addAccount(tempacc))
							{
								System.out.println("Account successfully created!");
								displayMenu(bank.getBankName());
								int usr = menuChoice();
								reloadMenu(usr, bank);
							}
						}
				}
			}
				
			finally {
		            if (scanner != null) {
		            }
			}
		}
	}
	public static int menuChoice()
	{
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nPlease enter your choice: ");
			int usrchoice = scanner.nextInt();
			if(usrchoice > 7 || usrchoice < 0)
				System.out.println("Invalid user choice! Try again!");
			return usrchoice;	
	}
	
	public static void displayAccount(Account acc)
	{
		System.out.println(acc);
	}
	public static void closeAccount(RemoteBank bank) throws RemoteException
	{
		
		System.out.println("\nEnter account number of the account you want to remove: ");
		String accnum =  scanner.nextLine();
		Account racc = null;
		racc = bank.removeAccount(accnum);
		if(racc != null)
		{
			System.out.println("Account successfully removed.\n");
			System.out.println(racc.toString());
			displayMenu(bank.getBankName());
			int usr = menuChoice();
			reloadMenu(usr, bank);
		}
		else
			System.out.println("No account found with that account number!\n");
			displayMenu(bank.getBankName());
			int usr = menuChoice();
			reloadMenu(usr, bank);
	}
	public static void depositMoney(RemoteBank bank) throws RemoteException
	{
		while(true)
		{
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nEnter account number you want to deposit into: ");
			String anum = scanner.nextLine();
			Account tempacc = bank.removeAccount(anum);
			if(tempacc == null)
				System.out.println("\nCan't find an account with the account number given! Try again!");
			else
			{
				System.out.println(tempacc.toString());
				System.out.println("\nEnter amount to deposit: ");
				double value = scanner.nextDouble();
				tempacc.deposit(value);
				bank.addAccount(tempacc);
				displayMenu(bank.getBankName());
				int usr = menuChoice();
				reloadMenu(usr, bank);
				break;
			}	
		}
	}
	public static void withdrawMoney(RemoteBank bank) throws RemoteException
	{
		String accname;
		Scanner scanner = new Scanner(System.in);
		while(true)
		{
			System.out.println("\nEnter account number you want to withdraw money from: ");
			String anum = scanner.nextLine();
			Account tempacc = bank.removeAccount(anum);
			if(tempacc == null)
				System.out.println("\nCan't find an account with the account number given! Try again!");
			else
			{
				System.out.println(tempacc.toString());
				System.out.println("\nEnter amount to withdraw: ");
				double value = scanner.nextDouble();
				tempacc.withdraw(value);
				bank.addAccount(tempacc);
				displayMenu(bank.getBankName());
				int usr = menuChoice();
				reloadMenu(usr, bank);
				break;
			}	
		}
	}
	public static void displayTax(RemoteBank bank) throws RemoteException
	{
		while(true)
		{
			System.out.println("\nEnter account name you want to view tax report from(Last Name, FirstName): ");
			String accname = scanner.nextLine();
			Account [] temparray = bank.searchByAccountName(accname);
				if(temparray.length > 0)
				{
					for(int i =0; i < temparray.length; i++)
					{
						if(temparray[i] instanceof Taxable)
						{
							System.out.println(temparray[i]);				
						}
					}
					displayMenu(bank.getBankName());
					int usr = menuChoice();
					reloadMenu(usr, bank);
					break;
				}
				else
					System.out.println("\nCan't find an account with the name given! Try again!");
		}

	}
	public static void displayAccOptions(RemoteBank sampleBank) throws RemoteException
	{
		System.out.println("How would you like to search for the bank account?");
		System.out.println("1 - Display account by account number.");
		System.out.println("2 - Display account by balance.");
		while(true)
		{
			System.out.println("\nPlease enter your choice: ");
			int usrchoice = scanner.nextInt();
			if(usrchoice > 2 || usrchoice <= 0)
				System.out.println("Invalid user choice! Try again!");
			if(usrchoice == 1)
			{
				while(true)
				{
				
				System.out.println("Enter account number of the account you want to display: ");
				String accnum1 = scanner.next();
				Account tempacc = sampleBank.removeAccount(accnum1);
					if(tempacc == null)
						System.out.println("\nCan't find an account with the account number given! Try again!");
					else
					{
						displayAccount(tempacc);
						displayMenu(sampleBank.getBankName());
						int usr = menuChoice();
						reloadMenu(usr, sampleBank);
						break;
					}	
				}
			}
			if(usrchoice == 2)
			{
				System.out.println("Enter balance for the account(s) you want to display: ");
				while(true)
				{
					double bal = scanner.nextDouble();
					if(bal < 0.00)
					{
						System.out.println("Invalid account balance! Try again!");
					}
					else
					{
						Account[] tempaccs = sampleBank.searchByBal(bal);
						Account tempacc;
						if(tempaccs.length > 0)
						{
							for(int i =0; i < tempaccs.length; i++)
							{
								tempacc = tempaccs[0];
								displayAccount(tempacc);
							}
							displayMenu(sampleBank.getBankName());
							int usr = menuChoice();
							reloadMenu(usr, sampleBank);
							break;
						}
						else
							System.out.println("No account found.\n");
						break;
					}
				}
			}
		}//end of while-true loop
	}
	public static void reloadMenu(int choice, RemoteBank sampleBank) throws RemoteException
	{
		Scanner scanner = new Scanner(System.in);
		switch(choice)
		{
		case 1:
			openAccount(sampleBank);
			break;
		case 2:
			closeAccount(sampleBank);
			break;
		case 3:
			depositMoney(sampleBank);
			break;
		case 4:
			withdrawMoney(sampleBank);
			break;
		case 5:
			displayAccOptions(sampleBank);
		case 6: 
			displayTax(sampleBank);
			break;
		case 7:
			System.out.println("\nThank you!");
			scanner.close();
			System.exit(0);
			break;
		}
	}
	public static void main(String[] args) 
	{
	       String url = "rmi://localhost:5678/";
		   try 
		   {
				System.out.println( "Starting bank client..." );
				RemoteBank bankServ = (RemoteBank) Naming.lookup( url + "Central" );
				System.out.println("Bank name is " + bankServ.getBankName());
				
				displayMenu("Central");
				int usrchoice = menuChoice();
				reloadMenu(usrchoice, bankServ);
		   }

		   catch(Exception e) 
		   {
					 System.out.println( "Error " + e );
		   }
		   System.out.println( "\nTerminating bank client..." );
	}

}
