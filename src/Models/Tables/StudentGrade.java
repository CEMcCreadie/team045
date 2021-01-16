package Models.Tables;

import Models.CourseStructure.LevelOfStudy;
import Models.DatabaseBehaviours.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class StudentGrade {

    private int regNumber;
    private String moduleCode;
    private String forename;
    private String surname;
    private LevelOfStudy levelOfStudyTaken;
    private int grade;
    private boolean resit;

    protected final String url = "jdbc:mysql://stusql.dcs.shef.ac.uk/team045";
    protected final String user = "team045" ;
    protected final String password = "5e15b333";

    public StudentGrade(int regNumber, String moduleCode, String forename, String surname, int grade, boolean resit, LevelOfStudy levelOfStudy) {
        this.regNumber = regNumber;
        this.moduleCode = moduleCode;
        this.forename = forename;
        this.surname = surname;
        this.grade = grade;
        this.resit = resit;
        this.levelOfStudyTaken = levelOfStudy;
    }

    public int getRegNumber() { return  regNumber;}

    public String getModuleCode() {return moduleCode;}

    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public String getLevelOfStudyTaken() {return levelOfStudyTaken.toString();}

    public int getGrade() {
        return grade;
    }

    public boolean getResit() {
        return resit;
    }

    public void setResit(Boolean bool){
        this.resit = bool;
        String query = "UPDATE StudentModule SET resit = " + this.resit + " WHERE regNumber = " + this.getRegNumber() + " AND moduleCode = '" + this.getModuleCode() + "' ;";
        DBController.executeCommand(query);
    }

    public void setGrade(int grade) {
        if (resit == true && grade > 40){
            this.grade = 40;
        } else {
            this.grade = grade;
        }
        String query = "UPDATE StudentModule SET grade = " + this.getGrade() + " WHERE regNumber = '" + this.getRegNumber() + "' AND moduleCode = '" + this.getModuleCode() + "' ;";
        DBController.executeCommand(query);
    }

}
