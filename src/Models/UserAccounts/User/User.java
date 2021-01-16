package Models.UserAccounts.User;

import Models.DatabaseBehaviours.DBController;

import java.sql.*;

public class User {
	
	private String username;
	private String forename;
	private String surname;
	private String emailAddress;

	protected final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
	protected final String user = "team045" ;
	protected final String password = "5e15b333";
	//for adding a new user to the DB
	public User(String forename,String surname) {
		this.forename = forename;
		this.surname = surname;
		this.username = createUsername(forename,surname);
		this.emailAddress = createEmail(createUsername(forename,surname));
	}
	//for editing a user that is already in the DB
	public User(String username,String forename,String surname,String emailAddress) {
		this.username = username;
		this.forename = forename;
		this.surname = surname;
		this.emailAddress = emailAddress;
	}
	
	public String getUsername() {
		return username;
	}
	protected void setUsername(String username) {
	    this.username = username;
	  }
	
	public String getSurname() {
		return surname;
	}
	protected void setSurname(String surname) {
	    this.surname = surname;
	  }
	
	public String getForename() {
		return forename;
	}
	protected void setForename(String forename) {
	    this.forename = forename;
	  }
	
	public String getEmailAddress() {
		return emailAddress;
	}
	protected void setEmailAddress(String emailAddress) {
	    this.emailAddress = emailAddress;
	}

	public String getUserDetails() {
		return getUsername() + "','" + getForename() + "','" + getSurname() + "','" + getEmailAddress();
	}
	/*
	 * automatacli return the username of the user by providing the Forename and the surname(s)
	 * @param String Forename
	 * @param String Surname
	 * @return the username
	 */
	public String createUsername(String forename,String surname){
			char firstForenameLetter = forename.toUpperCase().toCharArray()[0];
			char firstSurnameLetter = surname.toUpperCase().toCharArray()[0];
			char[] surInchar= surname.toCharArray();

			surInchar[0]=firstSurnameLetter;
			String temporaria = new String(surInchar);
			surname=temporaria;
			String username = firstForenameLetter+surname;
		    String usernameCount="";
		try (Connection con = DriverManager.getConnection(url,user,password)){
			Statement stmt = con.createStatement();
			ResultSet rs =  stmt.executeQuery("SELECT COUNT(username) \n" +
												   "FROM User \n"+
													"WHERE username LIKE \'%"+username+"%\';");
			rs.next();
			usernameCount = String.valueOf(rs.getInt(1));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		username = username+usernameCount;
		return username;
	}
	/*
	 * automatacli return the email of the user by providing the username
	 * @param String username
	 * @return email
	 */
	public String createEmail(String username){
		String email = username+"@shef.ac.uk";
		return email;
	}
	public boolean exists(){
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM User\n" +
																"WHERE username=? ;");
			pstmt.setString(1,this.getUsername());


			ResultSet rs = pstmt.executeQuery();
			return rs.isBeforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}


}