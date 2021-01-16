package Models.CourseStructure;

import Models.DatabaseBehaviours.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonalTutor implements CourseStructure {

    private int regNumber;
    private int employeeNumber;

    private String tableName = "PersonalTutor";
    private String primaryColumnName = "regNumber";

    public PersonalTutor() {}

    public PersonalTutor(int regNumber, int employeeNumber) {
        this.regNumber = regNumber;
        this.employeeNumber = employeeNumber;
    }

    @Override
    public String getCode(){
        return Integer.toString(regNumber);
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getPrimaryColumn() {
        return primaryColumnName;
    }

    @Override
    public ArrayList<Object> getVariables(){
        ArrayList<Object> list = new ArrayList<>();
        list.add(this.regNumber);
        list.add(this.employeeNumber);
        return list;
    }

    @Override
    public String[] getVariableNames() {return new String[] {"Reg Number","Employee Number"};}

    @Override
    public Class[] getVariableClass() {return new Class[] {Integer.class,Integer.class};}

    @Override
    public List<PersonalTutor> getAll(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<PersonalTutor> personalTutors  = new ArrayList<>();
            String query = "SELECT * FROM " + getTableName();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int regNumber = rs.getInt("regNumber");
                int employeeNumber = rs.getInt("employeeNumber");
                personalTutors.add(new PersonalTutor(regNumber,employeeNumber));
            }
            return personalTutors;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(){
        String values =  this.regNumber + "'," + this.employeeNumber;
        DBController.executeCommand("INSERT INTO PersonalTutor VALUES ('"+values+");");
    }

    @Override
    public void remove(){
        // We dont want to delete the row just set teacher to null1
        String query = "UPDATE PersonalTutor SET employeeNumber = null WHERE employeeNumber =" + this.employeeNumber + " AND regNumber = " + this.regNumber + " ;";
        DBController.executeCommand(query);
    }

}
