package Models.CourseStructure.Degree;

import Models.CourseStructure.CourseStructure;
import Models.DatabaseBehaviours.DBController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DegreeDepartment implements CourseStructure {
    private int uniqueId;
    private String departmentCode;
    private String degreeCode;

    private String tableName = "DegreeDepartment";
    private String primaryColumnName = "uniqueID";


    public DegreeDepartment() {}

    public DegreeDepartment(int uniqueId,String departmentCode,String degreeCode) {
        this.uniqueId = uniqueId;
        this.degreeCode = degreeCode;
        this.departmentCode = departmentCode;
    }

    @Override
    public String getCode(){
        return Integer.toString(uniqueId);
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
        list.add(this.uniqueId);
        list.add(this.departmentCode);
        list.add(this.degreeCode);
        return list;
    }

    @Override
    public String[] getVariableNames() {return new String[] {"Unique ID","Department Code","Degree Code"};}

    @Override
    public Class[] getVariableClass() {return new Class[] {Integer.class,String.class,String.class};}

    @Override
    public List<DegreeDepartment> getAll(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<DegreeDepartment> degreeDepartments = new ArrayList<>();
            String query = "SELECT * FROM " + getTableName();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int uniqueId = rs.getInt("uniqueID");
                String departmentCode = rs.getString("departmentCode");
                String degreeCode = rs.getString("degreeCode");
                degreeDepartments.add(new DegreeDepartment(uniqueId,departmentCode,degreeCode));
            }
            return degreeDepartments;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO DegreeDepartment (departmentCode,degreeCode)\n" +
                    "VALUES (?,?);");

            pstmt.setString(1,this.departmentCode);
            pstmt.setString(2,this.degreeCode);
            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void remove(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("DELETE FROM DegreeDepartment \n " +
                                                               "WHERE departmentCode =? AND degreeCode =? ;");
            pstmt.setString(1,this.departmentCode);
            pstmt.setString(2,this.degreeCode);
            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
