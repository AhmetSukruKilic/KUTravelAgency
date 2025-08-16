package users;

import java.util.HashSet;
import java.util.Set;
import generalMessages.*;
import java.util.Base64;

public abstract class User {
	protected String name;
	protected String role;
	protected String encodedPassword;
	protected int ID;
	protected static HashSet<User> validUsers = new HashSet<User>();

	protected User(String name, String password, String role, int ID) {
		this.name = name;
		this.role = role;
		this.ID = ID;
		this.encodedPassword = encode(password);
		validUsers.add(this);
	}
	
	protected static String encode(String word) {
		return Base64.getEncoder().encodeToString(word.getBytes());
	}
	
    protected static String decode(String word) { 
        return new String(Base64.getDecoder().decode(word));
    }
	
	private static boolean isNameSame(User validUser, String name) {
		return validUser.getName().equals(name);
	}
	
	private static boolean isPasswordSame(User validUser, String password) {
		return decode(validUser.getEncodedPassword()).equals(password);
	}
	
	private static boolean isUserSame(User validUser, String name, String password) {
		return isNameSame(validUser, name) && isPasswordSame(validUser, password);
	}
	
	public static User isValidUser(String name, String password) {
		for (final User validUser: validUsers) {
			if (isUserSame(validUser, name, password)) {
				new AcceptedMessage("Access Granted", "Welcome back " + validUser.role +  " " + name);
				return validUser;
			}
		}
		new WarningMessage("Access Denied", "Invalid name or password.");
		return null;
	}
	
	private static boolean isContainPunctuation(String name) {
	    Set<Character> puncs = Set.of(
	            '!', '"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/',
	            ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '_', '`', '{', '|', '}', '~');
	    for (char ch : name.toCharArray()) {
	        if (puncs.contains(ch)) {
	            return true;
	        }
	    }
	    return false;
	}


	
	private static boolean isPasswordValid(String password) {
		if (password.isEmpty()) {
			new WarningMessage("Invalid Password", "Password cannot be empty.");
			return false;
		}
		
		if (password.contains(",")) {
			new WarningMessage("Invalid Password", "Password cannot contain comma.");
			return false;
		}
		return true;
	}
	
	
	public static boolean isValidNewUser(String name, String password) {
		if (isContainPunctuation(name)) {
			new WarningMessage("Invalid Name", "Name contains invalid punctuation(s) or space(s).");
			return false;
		}
		
		if (!isPasswordValid(password)) {
			return false;
		}
		
		
		for (final User validUser: validUsers) {
			if (isNameSame(validUser, name)) {
				new WarningMessage("Invalid Name", "Name is taken by another user.");
				return false;
			}
		}
		return true;
	}
	
	

	public boolean isAdmin() {
		return this instanceof Admin;
	}
	
	public boolean isCustomer() {
		return this instanceof Customer;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @return the encodedPassword
	 */
	public String getEncodedPassword() {
		return encodedPassword;
	}

	
	
	
}
