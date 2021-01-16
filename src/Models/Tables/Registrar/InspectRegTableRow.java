package Models.Tables.Registrar;

import Models.CourseStructure.LevelOfStudy;
import Models.DatabaseBehaviours.DBController;
import Models.UserAccounts.Student.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

// Todo rename class
public class InspectRegTableRow {

    private String moduleCode;
    private String moduleName;
    private String grade;
    private int credits;

    public LevelOfStudy getLevelOfStudyTaken() {
        return levelOfStudyTaken;
    }

    private LevelOfStudy levelOfStudyTaken;

    public String getModuleCode() {
        return moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getGrade() {
        return grade;
    }

    public int getCredits() {
        return credits;
    }

    public InspectRegTableRow(String moduleCode, String moduleName,String grade, int credits, LevelOfStudy levelOfStudyTaken) {
        this.moduleCode = moduleCode;
        this.moduleName = moduleName;
        this.grade = grade;
        this.credits = credits;
        this.levelOfStudyTaken = levelOfStudyTaken;
    }




}
