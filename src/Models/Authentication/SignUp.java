package Models.Authentication;

import Models.DatabaseBehaviours.DBController;
import Models.Tables.StudentGrade;
import Models.UserAccounts.User;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

public class SignUp {

    public static void signUpUser(User user, String password){
        Password password1 = new Password(password);
        String hash = password1.get_SHA_384_SecurePassword();
        String salt = Base64.getEncoder().encodeToString(password1.getSalt());
        String values = user.getUsername() + "','" + hash + "','" + salt;
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            String query = "INSERT INTO Password VALUES ('" + values + "');";
            stmt.execute(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main (String[] args ) {
        User user = new User("Lembrei","bobby","williams","cmccrea@gmail.com");
        signUpUser(user,"test123");
    }
}