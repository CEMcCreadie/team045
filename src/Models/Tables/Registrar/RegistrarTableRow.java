package Models.Tables.Registrar;


import Models.DatabaseBehaviours.DBController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegistrarTableRow {
private int regNumber;
private String userName;
private String degreeCode;
private String levelOfStudy;
private String foreName;
private String surName;
private String email;

    public int getRegNumber() {
        return regNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getDegreeCode() {
        return degreeCode;
    }

    public String getLevelOfStudy() {
        return levelOfStudy;
    }

    public String getForeName() {
        return foreName;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public RegistrarTableRow(int regNumber, String userName, String degreeCode, String levelOfStudy, String foreName, String surName, String email) {
        this.regNumber = regNumber;
        this.userName = userName;
        this.degreeCode = degreeCode;
        this.levelOfStudy = levelOfStudy;
        this.foreName = foreName;
        this.surName = surName;
        this.email = email;
    }

    public void setRegistration(String degreeCode,String levelOfStudy){
        this.degreeCode = degreeCode;
        this.levelOfStudy = levelOfStudy;
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            String query = "UPDATE Student SET degreeCode = \"" + degreeCode + "\", levelOfStudy = \"" + levelOfStudy + "\" WHERE regNumber = " + regNumber + ";" ;

            stmt.execute(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void unassignRegistration(){
        this.degreeCode = null;
        this.levelOfStudy = null;
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            String query = "UPDATE Student SET degreeCode = " + null + ", levelOfStudy = " + null + " WHERE regNumber = " + regNumber + ";" ;

            stmt.execute(query);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
