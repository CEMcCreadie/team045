package Models.UserAccounts.Employee;

import Models.UserAccounts.User.User;
import Models.UserAccounts.User.UserType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public abstract class Employee extends User {
	
	private int employeeNumber;

	//for adding a new employee to the DB
	public Employee(String forename,String surname){
		super(forename, surname);
		this.employeeNumber = generateNewEmployeeNumber() ;
	}

	// dummy constructor
	public Employee(){
		super("","","","");
		this.employeeNumber = 0;
	}

	public Employee(String username,String forename,String surname,String emailAddress,int employeeNumber){
		super(username, forename, surname, emailAddress);
		this.employeeNumber = employeeNumber;
	}

	public abstract UserType getRole();

	public int getEmployeeNumber() {
		return employeeNumber;
	}

	/**
	 * genarates the new employeeNumber for the new employee being created\added
	 * @return newEmployeeNumber
	 */
	public int generateNewEmployeeNumber(){
		int genaratedEmployeeNumber=0;
		try (Connection con = DriverManager.getConnection(this.url,this.user,this.password)){

			String query = "SELECT COUNT(employeeNumber) \n"+
					"FROM  Employee;";
			Statement stmt = con.createStatement();
			ResultSet rs =  stmt.executeQuery(query);
			rs.next();
			genaratedEmployeeNumber = rs.getInt(1)+1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return genaratedEmployeeNumber;
	}
  
	public void setEmployeeNumber(int employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public boolean equals(Employee employee){
		return this.getUsername().equals(employee.getUsername()) && this.getForename().equals(employee.getForename()) &&
				this.getEmployeeNumber() == employee.getEmployeeNumber();

	}

}