package controller.manager;

import model.UserBean;
import model.dao.UserDao;

public class LoggingManager {
	
	public static int userID = 1;
	
	private static LoggingManager instance;
	
	private LoggingManager() {
		// userId = TODO get last id from DB or collection
	}
	
	public static synchronized LoggingManager getInstance() {
		if(instance == null) {
			instance = new LoggingManager();
		}
		return instance;
	}
	
	//========================REGISTER PART===================================//
	
		public void register(UserBean user) {
			boolean correctUsername = false;
			boolean correctPassword = false;
			boolean correctFirstName = false;
			boolean correctLastName = false;
			boolean correctEmail = false;
			
			if(validateUsername(user.getUsername())){
				correctUsername = true;
			}
			else {
				System.out.println("Incorrect username!");
			}
			
			if(validatePassword(user.getPassword())) {
				correctPassword = true;
			}
			else {
				System.out.println("Incorrect password!");
				System.out.println("Please note that the password must contains at least one uppercase letter"
						+ ", one lowercase letter, one digit, one special character and must be at least 8 characters long!");
			}
			if(validateFirstName(user.getFirstName())) {
				correctFirstName = true;
			}
			else {
				System.out.println("Incorrect first name!");
			}
			
			if(validateLastName(user.getLastName())) {
				correctLastName = true;
			}
			else {
				System.out.println("Incorrect last name!");
			}
			
			if(validateEmailAddress(user.getEmail())) {
				correctEmail = true;
			}
			else {
				System.out.println("Incorrect email address!");
			}
			
			if(correctUsername && correctPassword && correctFirstName && correctLastName && correctEmail) {
				user.setId(userID);
				userID++;
				//UserDao.getInstance().getUsers().put(user.getUsername(),user); // TODO add to DB and Collection
				UserDao.getInstance().registerUser(user);
				System.out.println(user.getUsername() + " has been successfully registered.");
				
			}
		}

		// validate username
		public boolean validateUsername(String username) {
			if (username != null && !username.isEmpty() && !UserDao.getInstance().getUsers().containsKey(username) && username.matches("^[a-zA-Z0-9]+([_ -]?[a-zA-Z0-9])*$"))
				return true;
			else
				return false;
		}

		/*
		 * This regex will enforce these rules for password:
		 * 
		 * At least one upper case English letter, (?=.*?[A-Z])
		 * At least one lower case English letter, (?=.*?[a-z])
		 * At least one digit, (?=.*?[0-9]) 
		 * Minimum eight in length .{8,} (with the anchors)
		 */
		public boolean validatePassword(String password) {
			return (password != null && !password.isEmpty()
					&& password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$")) ? true : false;
		}

		// validate first name
		public boolean validateFirstName(String firstName) {
			return firstName.matches("[A-Z][a-zA-Z]*");
		}

		// validate last name
		public boolean validateLastName(String lastName) {
			return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
		}

		// validate email address
		public boolean validateEmailAddress(String email) {
			String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
			java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
			java.util.regex.Matcher m = p.matcher(email);
			return m.matches();
		}

		//================================================================//
		
		//Logging by user object
		public boolean login(UserBean user) {
			return login(user.getUsername(), user.getPassword());
		}
		
		//Logging by username and password
		public boolean login(String userName, String password) {
			UserBean u = UserDao.getInstance().getUsers().get(userName);
			if(u == null) {
				System.out.println("Wrong username!");
				return false;
			}
			if(!u.getPassword().equals(password)){
				System.out.println("Wrong password");
				return false;
			}
			return true;
		}

}
