package Models.Authentication;

import Models.DatabaseBehaviours.DBController;
import Models.UserAccounts.User.User;

import java.sql.*;
import java.util.Base64;

public class SignUp {
    /**
     * This method Assigns a password to the desired user
     * @param user , user object to signUp
     * @param password , the password to be assigned to the user
     */
    public static void signUpUser(User user, String password){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Password password1 = new Password(password);
            // Convert plain text password into hash
            String hash = password1.get_SHA_384_SecurePassword();
            // Convert byte[] salt into plain text, for inserting into db
            String salt = Base64.getEncoder().encodeToString(password1.getSalt());
            String username = user.getUsername();
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO Password (username,password,salt)\n" +
                    "VALUES (?,?,?);");
            pstmt.setString(1,username);
            pstmt.setString(2,hash);
            pstmt.setString(3,salt);
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
