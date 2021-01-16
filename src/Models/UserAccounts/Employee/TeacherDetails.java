package Models.UserAccounts.Employee;

import Models.DatabaseBehaviours.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherDetails {

    public static String[] getAllTeachersNamesAndNumbers(){
        List<String> names = new ArrayList<>();
        try(Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement statement = con.createStatement();
            String query = "SELECT * FROM Employee JOIN User on User.username = Employee.username WHERE role = 'Teacher' ;";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()){
                int employeeNumber = rs.getInt("employeeNumber");
                String forename = rs.getString("forename");
                String surname = rs.getString("surname");
                names.add(employeeNumber + "-- EmployeeNumber, " +forename+ " " + surname + " -- FullName");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return names.toArray(new String[names.size()]);
    }

    public static int deCypherEmployeeNumber(String nameAndNumber){
        int index = nameAndNumber.indexOf('-');
        String refactoredString = nameAndNumber.substring(0,index);
        return Integer.parseInt(refactoredString);
    }

}
