package Controllers.Registrar;

import Models.CourseStructure.Module.CompulsoryModule;
import Models.CourseStructure.Module.CompusloryModuleConstraint;
import Models.DatabaseBehaviours.DBController;
import Models.Tables.Registrar.InspectRegTableRow;
import Models.UserAccounts.Student.*;;
import Views.Registrar.InspectRegistration;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InspectRegistrationController {
    private InspectRegistration inspectionFrame;

    public InspectRegistrationController(Student student){
        this.inspectionFrame = new InspectRegistration(student,this);
        inspectionFrame.setVisible(true);
    }

    public List<String> dataForModuleCombo(Student student){
        List<String> moduleCodes = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

            PreparedStatement pstmt = con.prepareStatement("SELECT moduleCode " +
                    "FROM Module WHERE moduleCode NOT IN (SELECT moduleCode FROM StudentModule WHERE regNumber = ?)");

            pstmt.setInt(1,student.getRegNumber());
            ResultSet rs =  pstmt.executeQuery();
            while(rs.next()){
                moduleCodes.add(rs.getString("moduleCode"));
                }
            return moduleCodes;
        } catch (Exception ex) {
                ex.printStackTrace();
                 }
        return null;
    }

    public void assignOptionalModule(String moduleCode, Student student){

            try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

                PreparedStatement pstmt = con.prepareStatement("INSERT INTO StudentModule (regNumber, moduleCode, grade, resit, levelOfStudyTaken) " +
                        "VALUES(?,?,?, FALSE, \"" + "TWO\") ;");

                pstmt.setInt(1,student.getRegNumber());
                pstmt.setString( 2,moduleCode);
                pstmt.setInt(3,0 );
                int count = pstmt.executeUpdate();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

    }

    public void removeOptionalModule(InspectRegTableRow row, Student student) {
        // If trying to remove compulsory module issue exception
        if (CompulsoryModule.isCompulsoryModule(student.getDegreeCode(),row.getModuleCode())) new CompusloryModuleConstraint();
        String query = "DELETE FROM StudentModule WHERE regNumber = " + student.getRegNumber() + " AND moduleCode = \"" + row.getModuleCode() + "\";";
        DBController.executeCommand(query);
    }
}

