package Models.Tables.Admin;

import Models.DatabaseBehaviours.DBController;
import Models.DatabaseBehaviours.UserManipulator;
import Models.UserAccounts.User.UserType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserTableRow {

    private int employeeNumber;
    private int regNumber;
    private String username;
    private String forename;
    private String surname;
    private String email;
    private UserType userType;

    public UserTableRow(int employeeNumber, int regNumber, String username, String forename, String surname, String email, UserType userType) {
        this.employeeNumber = employeeNumber;
        this.regNumber = regNumber;
        this.username = username;
        this.forename = forename;
        this.surname = surname;
        this.email = email;
        this.userType = userType;
    }

    public void remove(){
        UserManipulator.remove(this.username,"User","username");
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public UserType getUserType() {
        return userType;
    }

    public static List<UserTableRow> getAllUserTableRow() {
        String query = "SELECT User.*, Employee.employeeNumber, Employee.role,Student.regNumber FROM User LEFT JOIN Student ON Student.username = User.username" +
                " LEFT JOIN Employee ON Employee.username = User.username;";

        List<UserTableRow> userTableRows = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int regNumber = rs.getInt("regNumber");
                int employeeNumber = rs.getInt("employeeNumber");
                String username = rs.getString("username");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String email = rs.getString("emailAddress");
                String role = rs.getString("role");
                UserType userRole;
                if (role == null){
                    userRole = UserType.STUDENT;
                } else {
                    userRole = UserType.valueOf(rs.getString("role").toUpperCase());
                }
                userTableRows.add(new UserTableRow(employeeNumber,regNumber,username,forename,surname,email,userRole));
            }
            return userTableRows;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
