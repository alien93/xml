package entities;

public class Alderman extends Citizen{

	String username;
	String password;
	
	public Alderman(){}
	
	public Alderman(String username, String password){
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Alderman [username=" + username + ", password=" + password + "]";
	}
}
