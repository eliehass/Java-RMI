import java.rmi.*;

public interface Manager extends Remote
{
	public int connect (String userName, String password) throws RemoteException;
	public void changePassword (String userName, String oldPass, String newPass) throws RemoteException;
	public void createAccount (String userName, String password) throws RemoteException;
}//Manager
