import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.Vector;


public class AccountServer implements Manager
{
	static int port = 1576;
	static String address = "74.72.221.183";
	static Registry registry;
	static Vector<Account> accountVector = new Vector<Account>();
	Vector<Account> myAccountVector = accountVector;
	
	//add the admin
	
	public AccountServer() throws RemoteException
	{
		super();
	}

	public int connect(String userName, String password) throws RemoteException 
	{
		System.out.println("In connect method");
		if((userName.equals("admin")) && (password.equals("admin")))
			return 2;
		//create an account for comparison
		Account tempAccount = new Account( userName, password);
		for(int i = 0; i < accountVector.size(); i++)
		{
			if(tempAccount.compareTo(accountVector.elementAt(i)) == 0)
				return 1;
		}
		return 0;
	}

	public void changePassword(String userName, String oldPass,  String newPass) throws RemoteException 
	{
		//create an account for comparison
		Account tempAccount = new Account( userName, oldPass);
		for(int i = 0; i < accountVector.size(); i++)
		{
			if(tempAccount.compareTo(accountVector.elementAt(i)) == 0)
			{
				accountVector.remove(i);
				accountVector.add(new Account(userName, newPass));
				accountVector.elementAt(i).password = newPass;
				break;
			}
		}
		
	}

	public void createAccount(String userName, String password) throws RemoteException 
	{
		System.out.println("In createAccount method");
		Account newAccount = new Account(userName, password);
		accountVector.add(newAccount);
		
	}
	
	public static void main(String args[])
	{
		accountVector.add(new Account("admin", "admin"));
		System.setProperty("sun.rmi.transport.tcp.responseTimeout", "900000");
		System.setProperty("java.security.policy","file:/Users/eliehass/Documents/CS 344/RMI/bin/myPolicy.java.policy");
		if(System.getSecurityManager() == null)
		{
			System.setSecurityManager(new SecurityManager());
		}

		try{
			Manager server = new AccountServer();
			Manager stub = (Manager) UnicastRemoteObject.exportObject(server, port);
			System.out.println("created admin");
			registry = LocateRegistry.createRegistry(port);
			System.out.println("created registry");
			registry.rebind("AccountServer", stub);
			System.out.println("rebinded registry");

		}catch(RemoteException ex){
			ex.printStackTrace();
		}
	}
}
