package Models.Authentication;

import Models.DatabaseBehaviours.DBController;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Base64;

public class Login {

    /**
     * this method is used to authenticate the login That is being executed
     * @param username , users username
     * @param password , the password assigned to the inputed username( if it is right then True)
     * @return True if Login details are Right and False if they are incorrect
     */
    public static boolean loginAuthenticated(String username, String password){
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Password WHERE username =? ;");
            pstmt.setString(1,username);
            ResultSet rs = pstmt.executeQuery();
            String salt = "";
            String hash = "";
            while (rs.next()){
                hash = rs.getString("password");
                salt = rs.getString("salt");
            }
            byte[] bytes = Base64.getDecoder().decode(salt);
            Password password1 = new Password(password,bytes);
            return password1.get_SHA_384_SecurePassword().equals(hash);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
