package Models.DatabaseBehaviours;

import Models.UserAccounts.User.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UserManipulator {

    public static void remove(String toRemove,String tableName, String primaryKey){
        String query = "DELETE FROM " + tableName + " WHERE " + primaryKey + " = '"+toRemove+"';";
        DBController.executeCommand(query);
    }

    /**
     * This method adds a user object to the DB
     * @param user
     */
    public static void addUser(User user) {

        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            String[] userdetails = user.getUserDetails().trim().replace("','","#").split("#");
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO User (username,forename,surname,emailAddress)\n" +
                    "VALUES (?,?,?,?);");
            pstmt.setString(1,userdetails[0]);
            pstmt.setString(2,userdetails[1]);
            pstmt.setString(3,userdetails[2]);
            pstmt.setString(4,userdetails[3]);

            int count = pstmt.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


}
