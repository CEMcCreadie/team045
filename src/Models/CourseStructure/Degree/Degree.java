package Models.CourseStructure.Degree;

import Models.CourseStructure.CourseStructure;
import Models.CourseStructure.Qualification;
import Models.DatabaseBehaviours.DBController;
import Models.DatabaseBehaviours.UserManipulator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Degree implements CourseStructure {

    private final String tableName = "Degree";
    private final String primaryColumnName = "degreeCode";

    private String degreeCode;
    private String courseName;
    private int lengthOfStudy;
    private boolean yearInIndustry;
    private Qualification qualification;

    public Degree(){}

    //Constructor where only degreeCode is needed (remove,exists)
    public Degree(String degreeCode){
        this.degreeCode = degreeCode;
        this.courseName = "Dummy Degree";
        this.lengthOfStudy = 3;
        this.yearInIndustry = false;
    }

    public Degree(String degreeCode, String courseName, int lengthOfStudy, boolean yearInIndustry, Qualification qualification){
        this.degreeCode = degreeCode;
        this.courseName = courseName;
        this.lengthOfStudy = lengthOfStudy;
        this.yearInIndustry = yearInIndustry;
        this.qualification = qualification;
    }

    /**
     * Turn True to 1 and False to 0
     * @param x
     * @return 0,1
     */
    public int boolToInt(boolean x){
        return x ? 1 : 0 ;
    }
    /**
     * Turn 1 to True and 0 to False
     * @param x
     * @return 0,1
     */
    public boolean IntToBool(int x){
        return x == 1;
    }

    public Qualification getQualification() {
        return qualification;
    }

    @Override
    public String getCode() {
        return this.degreeCode;
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
        list.add(this.degreeCode);
        list.add(this.courseName);
        list.add(this.lengthOfStudy);
        list.add(this.yearInIndustry);
        list.add(this.qualification);
        return list;
    }

    @Override
    public String[] getVariableNames(){ return new String[] {"Degree Code","Course Name","Length Of Study","Year in Industry","Qualification"};}

    @Override
    public Class[] getVariableClass(){ return new Class[] {String.class,String.class,Integer.class,Boolean.class,String.class};}

    public void add(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Degree (degreeCode,courseName,lengthOfStudy,yearInIndustry,qualification)\n" +
                    "VALUES (?,?,?,?,?);");
            pstmt.setString(1,this.degreeCode);
            pstmt.setString(2,this.courseName);
            pstmt.setInt(3,this.lengthOfStudy);
            pstmt.setInt(4,this.boolToInt(this.yearInIndustry));
            pstmt.setString(5,this.qualification.toString());
            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }



    public void remove(){
        UserManipulator userManipulator = new UserManipulator();
        userManipulator.remove(this.degreeCode,"Degree","degreeCode");
    }

    public List<Degree> getAll(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<Degree> degrees = new ArrayList<>();
            String query = "SELECT * FROM " + this.getTableName();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                String degreeCode = rs.getString("degreeCode");
                String courseName = rs.getString("courseName");
                int lengthOfStudy = rs.getInt("lengthOfStudy");
                int yearInIndustry = rs.getInt("yearInIndustry");
                Qualification qualification = Qualification.valueOf(rs.getString("qualification"));
                degrees.add(new Degree(degreeCode,courseName,lengthOfStudy,this.IntToBool(yearInIndustry),qualification));
            }
            return degrees;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * @return a String array with all Degree code inside the Degree table in the DB
     */
    public static String[] getAllDegreeCodes(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            List<String> degreeCodes = new ArrayList<>();
            String query = "SELECT * FROM Degree";
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                String degreeCode = rs.getString("degreeCode");
                degreeCodes.add(degreeCode);
            }
            return degreeCodes.toArray(new String[degreeCodes.size()]);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String generateDegreeCode(String departmentCode, String degreeSignature){
        return departmentCode + degreeSignature;
    }

    //Getters

    public String getDegreeCode(){
        return degreeCode;
    };

    public String getCourseName(){
        return courseName;
    };

    public int getLengthOfStudy(){
        return lengthOfStudy;
    }

    public boolean getYearInIndustry(){
        return yearInIndustry;
    }


}
