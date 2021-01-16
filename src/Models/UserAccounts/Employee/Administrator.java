package Models.UserAccounts.Employee;

import Models.CourseStructure.Degree.Degree;
import Models.CourseStructure.Module.UniModule;
import Models.DatabaseBehaviours.DBController;
import Models.DatabaseBehaviours.UserManipulator;
import Models.CourseStructure.*;
import Models.UserAccounts.Student.*;
import Models.UserAccounts.User.UserAccountBuilder;
import Models.UserAccounts.User.UserType;;

import java.sql.*;

public class Administrator extends Employee {


    //for adding a new Admin to the DB
    public Administrator(String forename,String surname){
        super(forename, surname);
    }
    //for editing a admin that is already in the DB

    // Dummy Class
    public Administrator(){
        super();
    }

    public Administrator(String username,String forename,String surname,String emailAddress,int employeeNumber){
        super(username, forename, emailAddress, surname, employeeNumber);
    }

    @Override
    public UserType getRole(){
        return UserType.ADMIN;
    }

    public void addModule(String moduleCode,String moduleName,int credits){
        UniModule module = new UniModule(moduleCode, moduleName,credits);
        module.add();
    }

    public void removeModule(String moduleCode){
        UniModule module = new UniModule(moduleCode);
        module.remove();
    }


    public <T extends Employee> void addEmployee(T employee){
        UserManipulator.addUser(employee);
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Employee (employeeNumber,username,role)\n" +
                    "VALUES (?,?,?);");
            pstmt.setInt(1,employee.getEmployeeNumber());
            pstmt.setString(2,employee.getUsername());
            pstmt.setString(3,employee.getRole().toString());


            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void removeEmployee(int employeeNumber){

        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Employee\n" +
                    "WHERE employeeNumber =?;");
            pstmt.setInt(1,employeeNumber);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            String username = rs.getString("username");
            // Removing user will remove the associated employee
            UserManipulator.remove(username,"User","username");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addDegree(String degreeCode,String courseName, int lengthOfStudy, boolean yearInIndustry,Qualification qualification){
        Degree degree = new Degree(degreeCode,courseName,lengthOfStudy,yearInIndustry,qualification);
        degree.add();
    }

    public void removeDegree(String degreeCode){
        Degree degree = new Degree(degreeCode);
        degree.remove();
    }

    public void addUniversityDepartment(String departmentCode,String departmentName){
        Department department = new Department(departmentCode,departmentName);
        department.add();
    }

    public void removeUniversityDepartment(String departmentCode){
        Department department = new Department(departmentCode);
        department.remove();
    }
    public void addStudent(Student student){
        UserManipulator.addUser(student);
        String values = student.getStudentDetailsForInserting();
        DBController.executeCommand("INSERT INTO Student VALUES ('"+values+"');");
        Student student1 = new UserAccountBuilder(student.getUsername()).studentBuilder();
        DBController.executeCommand("INSERT INTO PersonalTutor (regNumber) VALUES ("+student1.getRegNumber()+");");
    }

    public void removeStudent(int regNumber){
        String studentRegNumber = Integer.toString(regNumber);
        final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
        final String user = "team045" ;
        final String password = "5e15b333";
        try (Connection con = DriverManager.getConnection(url,user,password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT * FROM Student WHERE regNumber = " + studentRegNumber);
            rs.next();
            String username = rs.getString("username");
            // Removing user will remove the associated student, due to cascade delete
            UserManipulator.remove(username,"User","username");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addCompulsoryModule(String degreeCode, String moduleCode, LevelOfStudy levelOfStudy){
        Degree degree = new Degree(degreeCode);
        UniModule module = new UniModule(moduleCode);
        if (degree.exists() & module.exists()){
            try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

                PreparedStatement pstmt = con.prepareStatement("INSERT INTO DegreeCompulsory (degreeCode,moduleCode,levelOfStudy)\n" +
                        "VALUES (?,?,?);");
                pstmt.setString(1,degreeCode);
                pstmt.setString(2,moduleCode);
                pstmt.setString(3,levelOfStudy.toString());

                int count = pstmt.executeUpdate();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public void addDegreeDepartment(String departmentCode,String degreeCode){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO DegreeDepartment (departmentCode,degreeCode)\n" +
                    "VALUES (?,?);");

            pstmt.setString(1,departmentCode);
            pstmt.setString(2,degreeCode);
            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
