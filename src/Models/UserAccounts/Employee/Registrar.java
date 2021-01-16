package Models.UserAccounts.Employee;

import Models.DatabaseBehaviours.DBController;
import Models.Tables.Registrar.RegistrarTableRow;
import Models.UserAccounts.Student.*;;
import Models.UserAccounts.User.UserType;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Registrar extends Employee {


    //for adding a new registrar to the DB
    public Registrar(String forename, String surname){
        super(forename, surname);
    }

    // Dummy Class
    public Registrar(){
        super();
    }

    public Registrar(String username, String forename, String surname, String emailAddress, int employeeNumber){
        super(username,forename, surname,emailAddress , employeeNumber);
    }

    @Override
    public UserType getRole(){
        return UserType.REGISTRAR;
    }


    // if a student has met all their credit requirements
    public boolean studentMetRequirement(Student student){
        return (student.getCreditsTaken() == student.getCreditRequirements());
    }

    public void autoEnroll(Student student){

    }


    public List<RegistrarTableRow> getAllStudents(){
        String query = "SELECT Student.regNumber, Student.userName, Student.degreeCode, Student.levelOfStudy, User.forename, User.surname, User.emailAddress from Student JOIN User ON Student.username = User.username;";

        List<RegistrarTableRow> registrarTableRows = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int regNumber = rs.getInt("regNumber");
                String userName = rs.getString("username");
                String degreeCode = rs.getString("degreeCode");
                String levelOfStudy = rs.getString("levelOfStudy");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                String emailAddress = rs.getString("emailAddress");
                registrarTableRows.add(new RegistrarTableRow(regNumber,userName,degreeCode,levelOfStudy,forename,surname,emailAddress));
            }
            return registrarTableRows;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Object[] getAllCourses() {
        String query = "SELECT degreeCode FROM Degree;";

        List<String> degreeCode = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(this.url,this.user,this.password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                degreeCode.add(rs.getString("degreeCode"));
            }
            return degreeCode.toArray();
            // Count should never be greater than one, I believe
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
