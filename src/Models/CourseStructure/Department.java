package Models.CourseStructure;

import Models.DatabaseBehaviours.DBController;
import Models.DatabaseBehaviours.UserManipulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Department implements CourseStructure{

    private final String tableName = "Department";
    private final String primaryColumnName = "departmentCode";
    private String departmentCode;
    private String departmentName;

    public Department(){}

    public Department(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Department(String departmentCode, String departmentName) {
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
    }

    public String getCode() {
        return departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getPrimaryColumn() {
        return primaryColumnName;
    }

    public void add(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Department (departmentCode,departmentName)\n" +
                                                                "VALUES (?,?);");
            pstmt.setString(1,this.departmentCode);
            pstmt.setString(2,this.departmentName);
            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    public void remove(){
        UserManipulator.remove(this.departmentCode,"Department","departmentCode");
    }

    public List<Department> getAll(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<Department> departments = new ArrayList<>();
            String query = "SELECT * FROM Department";
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                String departmentCode = rs.getString("departmentCode");
                String departmentName = rs.getString("departmentName");
                departments.add(new Department(departmentCode,departmentName));
            }
            return departments;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * @return a String array with all Department codes inside the Degree table in the DB
     */
    public static String[] getAllDepartmentCodes(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<String> departmentCodes = new ArrayList<>();
            String query = "SELECT * FROM Department";
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                String departmentCode = rs.getString("departmentCode");
                departmentCodes.add(departmentCode);
            }
            return departmentCodes.toArray(new String[departmentCodes.size()]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @param departmentName
     * @return the Department Code assign to the specified department name
     */
    public static String getCodeFromName(String departmentName){
        List<Department> departments = new Department().getAll();
        for (Department department : departments) {
            if (department.getDepartmentName().equals(departmentName)) return department.getCode();
        }
        return null;
    }

    public ArrayList<Object> getVariables(){
        ArrayList<Object> list = new ArrayList<>();
        list.add(this.departmentCode);
        list.add(this.departmentName);
        return list;
    }

    public String[] getVariableNames(){
        return new String[] {"Department Code","Department Name"};
    }

    public Class[] getVariableClass(){
        return new Class[] {String.class,String.class};
    }

}
