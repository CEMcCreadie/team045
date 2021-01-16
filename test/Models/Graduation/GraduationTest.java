package Models.Graduation;

import Models.CourseStructure.Qualification;
import Models.DatabaseBehaviours.DBController;
import Models.UserAccounts.Student.Student;
import Models.UserAccounts.User.UserAccountBuilder;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class GraduationTest {

    @Test
    public void graduateTest(){
        DBController.executeCommand("INSERT INTO StudentModule (regNumber,moduleCode,grade,resit,levelOfStudyTaken) VALUES (12347,'COM1012',80,0,'ONE');");
        DBController.executeCommand(" INSERT INTO StudentModule (regNumber,moduleCode,grade,resit,levelOfStudyTaken) VALUES (12347,'COM2012',80,0,'TWO');");
        DBController.executeCommand("INSERT INTO StudentModule (regNumber,moduleCode,grade,resit,levelOfStudyTaken) VALUES (12347,'COM3012',80,0,'THREE');");
        DBController.executeCommand(" INSERT INTO StudentModule (regNumber,moduleCode,grade,resit,levelOfStudyTaken) VALUES (12347,'COM4012',20,0,'P');");
        Student student = new UserAccountBuilder("GMann0").studentBuilder();
        try {
            student.graduate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Graduates WHERE regNumber = 12347");
            rs.next();
            assertEquals(DegreeClassification.valueOf(rs.getString("Achievement")),DegreeClassification.FIRST_CLASS);
            assertEquals(Qualification.valueOf(rs.getString("Qualification")),Qualification.BEng);
            DBController.executeCommand("DELETE FROM Graduates WHERE regNumber = 12347;");
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}