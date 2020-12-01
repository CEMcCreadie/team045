package Models.CourseStructure;

import Models.DatabaseBehaviours.DBController;
import Models.DatabaseBehaviours.UserManipulator;
import Models.UserAccounts.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UniModule implements CourseStructure{

    private String moduleName;
    private String moduleCode;
    private int credits;

    //Constructor where only degreeCode is needed (remove,exists)
    public UniModule(String moduleCode){
        this.moduleCode = moduleCode;
        this.credits = 20;
    }

    public UniModule(String moduleCode,String moduleName, int credits){
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.credits = credits;
    }

    public String getCode(){
        return this.moduleCode;
    }

    public void add(){
        String values = this.moduleCode + "','" + this.moduleName + "','" + this.credits;
        DBController.executeCommand("INSERT INTO Module VALUES ('"+values+"');");
    }

    public void remove(){
        UserManipulator userManipulator = new UserManipulator();
        userManipulator.remove(this.moduleCode,"Module","moduleCode");
    }

    public boolean exists(){
        try (Connection con = DriverManager.getConnection(this.url,this.user,this.password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT COUNT(1) FROM Module Where moduleCode = '"+moduleCode+"';");
            rs.next();
            int count = Integer.parseInt(rs.getString("COUNT(1)"));
            // Count should never be greater than one, I believe
            return count >= 1;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
