package rest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.fasterxml.jackson.databind.ObjectMapper;

import entities.User;
import entities.UserRole;

@Path("/user")
public class UserLoginREST {
	
	private static final String aPath = "/users/alderman.txt";
	private static final String pPath = "/users/president.txt";
	private static final String cPath = "/users/citizen.txt";
	
	@Path("/login")
	@POST
	public String login(String userJson){
		User newUser = null;
		try {
			newUser = new ObjectMapper().readValue(userJson, User.class);
			return checkUser(newUser);
		} catch (IOException e) {
			return "Greška na serveru. Pokušajte ponovo";
		}
	}
	
	private String checkUser(User user){
		
		String path = null;
		if(user.getRole().equals(UserRole.ALDERMAN)) path = aPath;
		else if(user.getRole().equals(UserRole.PRESIDENT)) path = pPath;
		else path = cPath;
		
		File f = new File(getClass().getResource(path).getPath());
		
		try {
			Scanner scanner = new Scanner(f);
			while(scanner.hasNext()){
				String line = scanner.nextLine();
				String[] splitStr = line.split("=");
				String username = splitStr[0].trim();
				String password = splitStr[1].trim();
				if(username.equals(user.getUsername())){
					if(password.equals(user.getPassword())){
						scanner.close();
						return "OK";
					}else{
						scanner.close();
						return "Neispravna lozinka";
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "Greška na serveru. Pokušajte ponovo";
		}
		return "Neispravno korisničko ime";
	}
}
