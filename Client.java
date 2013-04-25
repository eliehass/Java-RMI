import java.rmi.*;
import java.rmi.registry.*;
import java.io.*;
import java.util.*;

public class Client
{
	public static void main(String [] args)
	{
		Scanner sc = new Scanner(System.in);
		String userName, password;
		//file:/C:/Users/Esther/Documents/RMI_HW/myPolicy.java.policy
		String policy = null;
		//choice needs to be some number greater than 0 so the while loop will run
		int choice = 7, type;
		
		try
		{
			policy = args[0];
			System.setProperty("java.security.policy",policy);
			System.setProperty("sun.rmi.transport.tcp.responseTimeout", "900000");
			if(System.getSecurityManager() == null)
			{
				System.setSecurityManager(new SecurityManager());
			}//if
		}//try
		catch (Exception e)
		{
			System.out.println("Please enter the location of the policy you are using below. Enter anything if you have no policy file.");
			policy = sc.next();
			System.out.println();
		}//catch

		try
		{

			System.out.println("Got registry");
			Registry reg = LocateRegistry.getRegistry(1576);
			Manager m = (Manager) reg.lookup("AccountServer");
			System.out.println("Got manager");

			while(true)
			{
				choice = 7;
				System.out.print("\nWelcome to account headquarters!\nYou can choose from one of them options below\n(1)Login to the system\n(0)Exit this session entirely\n\nEnter the number of your choice here: ");
				int temp = sc.nextInt();
				if(temp == 0)
					break;
				System.out.print("\nEnter your user name: ");
				userName = sc.next();
				
				System.out.print("\nEnter your password: ");
				password = sc.next();
				
				type = m.connect(userName, password);
				if (type == 0)
				{
					System.out.println("\nYour username and/or password are NOT correct!");
					continue;
				}//if
					
				if (type == 1)
				{
					//choice needs to be != 0 to start with so that the while loop will run
					while(choice != 0)
					{
						System.out.print("\nYou have two options\n(1) Change your password\n(0) Exit this account\n\nEnter the number of your choice here: ");
						choice = sc.nextInt();
						if (choice == 1)
						{
							System.out.print("\nSure, just enter the new password here: ");
							String newPass = sc.next();
							m.changePassword(userName, password, newPass);
							password = newPass;
						}//if
					}//while
				}//if
				
				if (type == 2)
				{
					//choice needs to be != 0 to start with so that the while loop will run
					while(choice != 0)
					{
						System.out.print("\nYou have three options\n(1) Change your password\n(2) Create a new Account\n(0) Exit this account\n\nEnter the number of your choice here: ");
						choice = sc.nextInt();
						if (choice == 1)
						{
							System.out.print("\nSure, just enter the new password here: ");
							String newPass = sc.next();
							m.changePassword(userName, password, newPass);
							password = newPass;
						}//if
						if (choice == 2)
						{
							System.out.print("\nSure, just enter the user name here: ");
							String u = sc.next();
							System.out.print("\nAnd the password here: ");
							String p = sc.next();
							m.createAccount(u, p);
						}//if
					}//while
				}//if
			}//while
		}//try
		catch (Exception e)
		{
			System.out.println(e);
		}//catch
	}//main
}//Client
