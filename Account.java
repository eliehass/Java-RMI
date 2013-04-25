public class Account implements Comparable
{
	public String userName = "";
	public String password = "";
	public Account(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}
	
	public int compareTo(Object obj) {
		Account compAccount = (Account)obj;
		if((this.userName.equals(compAccount.userName)) && (this.password.equals(compAccount.password)))
			return 0;
		return 1;
	}
}
