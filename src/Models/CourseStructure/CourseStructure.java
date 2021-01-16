package Models.CourseStructure;

import Models.DatabaseBehaviours.DBController;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CourseStructure {

    final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
    final String user = "team045" ;
    final String password = "5e15b333";


    public void add();

    public void remove();

    /**
     * This method updates the table in the specified columnName to the new value
     * @param columnName , column to be updated
     * @param value , to which will be updated
     */
    public default void update(String columnName,String value){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("UPDATE" + this.getTableName()+"\n" +
                                                                "SET "+columnName+" =? \n" +
                                                                "WHERE "+this.getPrimaryColumn()+"=? ;");
            pstmt.setString(1,value);
            pstmt.setString(2,this.getCode());

            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Checks if any of the classes that implement CourseStructure Exist inside the DB
     * @return True if it exist and False if not
     */
    public default boolean exists(){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM "+this.getTableName()+"\n" +
                                                                "WHERE "+this.getPrimaryColumn()+"=? ;");
            pstmt.setString(1,this.getCode());


            ResultSet rs = pstmt.executeQuery();
            return rs.isBeforeFirst();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }

    /**
     * @return the current Table name
     */
    public String getTableName();

    /**
     * @return  It will either return the Unique Id of a table or simply return the Module or degree Codes
     */
    public String getCode();

    /**
     * @return it will return the column Name that contains the Primary Key
     */
    public String getPrimaryColumn();

    /**
     * @return it will return a List of type Specified as T
     */
    public <T extends CourseStructure> List<T> getAll();

    /**
     * @return it return the Variables that are contained in a object in an ArrayList of object type, because there will
     * be variables of type int and String
     */
    public ArrayList<Object> getVariables();

    /**
     * @return a string array with the variable names
     */
    public String[] getVariableNames();
    /**
     * @return a class array with the variable classes
     */
    public Class[] getVariableClass();

}
