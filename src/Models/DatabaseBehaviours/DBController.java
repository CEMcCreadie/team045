package Models.DatabaseBehaviours;

import com.mysql.cj.xdevapi.PreparableStatement;

import java.sql.*;
public class DBController {

    public final static String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
    public final static String user = "team045" ;
    public final static String password = "5e15b333";

    /**
     * This method executes the provided String as SqlCode
     * @param s
     */
    public static void executeCommand(String s){
        final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
        final String user = "team045" ;
        final String password = "5e15b333";
        try (Connection con = DriverManager.getConnection(url,user,password)){
            Statement stmt = con.createStatement();
            stmt.execute(s);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method Creates a Connection object that is connected to the DB
     * @return the Connection
     */
    public static Connection getConnection(){
        final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
        final String user = "team045" ;
        final String password = "5e15b333";
        try{
            return DriverManager.getConnection(url,user,password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * This method Creates a Statement
     * @return a Statement object
     */
    public static Statement createStatement(){

        try(Connection con = getConnection()){
            return con.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
