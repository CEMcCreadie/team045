package Models.CourseStructure.Module;

import Models.CourseStructure.CourseStructure;
import Models.DatabaseBehaviours.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeachesModule implements CourseStructure {

    private String moduleCode;
    private int employeeNumber;

    private String tableName = "TeachesModule";
    private String primaryColumnName = "moduleCode";

    public TeachesModule() {}

    public TeachesModule(String moduleCode, int employeeNumber) {
        this.moduleCode = moduleCode;
        this.employeeNumber = employeeNumber;
    }

    @Override
    public String getCode(){
        return (moduleCode);
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
        list.add(this.moduleCode);
        list.add(this.employeeNumber);
        return list;
    }

    @Override
    public String[] getVariableNames() {return new String[] {"Module Code","Employee Number"};}

    @Override
    public Class[] getVariableClass() {return new Class[] {String.class,Integer.class};}

    @Override
    public List<TeachesModule> getAll(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<TeachesModule> teachesModules = new ArrayList<>();
            String query = "SELECT * FROM " + getTableName();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                String moduleCode = rs.getString("moduleCode");
                int employeeNumber = rs.getInt("employeeNumber");
                teachesModules.add(new TeachesModule(moduleCode,employeeNumber));
            }
            return teachesModules;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(){
        String values =  this.moduleCode + "'," + this.employeeNumber;
        DBController.executeCommand("INSERT INTO TeachesModule VALUES ('"+values+");");
    }

    @Override
    public void remove(){
        // We dont want to delete the row just set teacher to null1
        String query = "UPDATE TeachesModule SET employeeNumber = null WHERE employeeNumber =" + this.employeeNumber + " AND moduleCode = '" + this.moduleCode + "' ;";
        DBController.executeCommand(query);
    }

}
