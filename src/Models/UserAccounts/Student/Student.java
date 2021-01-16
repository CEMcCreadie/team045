package Models.UserAccounts.Student;

import Models.CourseStructure.*;
import Models.CourseStructure.Degree.Degree;
import Models.CourseStructure.Degree.DegreeBuilder;
import Models.CourseStructure.Module.CompulsoryModule;
import Models.CourseStructure.Module.UniModule;
import Models.DatabaseBehaviours.DBController;
import Models.Graduation.GradeAttainmentConstraint;
import Models.Graduation.Graduation;
import Models.Graduation.LevelOfStudyConstraint;
import Models.Tables.Registrar.InspectRegTableRow;
import Models.Tables.StudentGrade;
import Models.UserAccounts.User.User;

import java.sql.*;
import java.util.*;

public class Student extends User {

	private int regNumber; 
	private String degreeCode;
	private LevelOfStudy levelOfStudy;

	//for adding a new Student to the DB
	public Student(String forename,String surname){
		super(forename,surname);
		// wont have decided yet when being added by admin
		this.degreeCode = null;
		// Fair to assume that students start at Level 1
		this.levelOfStudy = LevelOfStudy.ONE;
	}

	public Student(String forename,String surname, String degreeCode, String levelOfStudy) {
		super(forename, surname);
		this.degreeCode = degreeCode;
		this.levelOfStudy = LevelOfStudy.valueOf(levelOfStudy.toUpperCase());
	}

	//for editing a Student that is already in the DB
	public Student(String username,String forename,String surname,String emailAddress,int regNumber, String degreeCode, String levelOfStudy) {
		super(username, forename, surname, emailAddress);
		this.regNumber = regNumber;
		this.degreeCode = degreeCode;
		this.levelOfStudy = LevelOfStudy.valueOf(levelOfStudy.toUpperCase());
	}

	/**
	 * get information for adding a new student so
	 */
	public String getStudentDetailsForInserting() {
		return getRegNumber() +"','"+ getUsername() +"','" + getDegreeCode() + "','" + getLevelOfStudy();
	}


	public int getRegNumber() {
		return regNumber;
	}

	public String getDegreeCode() {
		return degreeCode;
	}

	public LevelOfStudy getLevelOfStudy() {
		return levelOfStudy;
	}

	public void setLevelOfStudy(LevelOfStudy levelOfStudy) {
		this.levelOfStudy = levelOfStudy;
	}

	public LevelOfStudy getNextLevelOfStudy(){
		return LevelOfStudy.getNext(this.levelOfStudy);
	}

	public Qualification getQualificationType(){
		Degree degree = DegreeBuilder.build(this.degreeCode);
		return degree.getQualification();
	}

	public void updateLevelOfStudy() throws InsufficientCreditEnrollment,InsufficientGradeAttainment,TooManyResits{
		if (this.metCredits() == false)	{
			throw new InsufficientCreditEnrollment();
		}
		if (this.metGrades() == false && this.alreadyRetaken()) {
			this.removeAllModules();
			throw new TooManyResits();
		}
		if (this.metGrades() == false){
			throw new InsufficientGradeAttainment();
		}
		LevelOfStudy nextLevel = this.getNextLevelOfStudy();
		if (nextLevel == this.getLevelOfStudy()){
			return;
		}
		this.setLevelOfStudy(nextLevel);
		// getlevel will now return update level
		String query = "UPDATE Student SET levelOfStudy = '" + this.getLevelOfStudy().toString() + "' WHERE regNumber = '" + this.getRegNumber() + "';";
		DBController.executeCommand(query);
		this.autoEnroll();
	}

	public boolean alreadyRetaken(){
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM StudentModule WHERE regNumber =" + this.getRegNumber() + " AND levelOfStudyTaken = '" + this.getLevelOfStudy().toString() + "' " +
					" AND resit = 1;";
			ResultSet rs = stmt.executeQuery(query);
			return rs.isBeforeFirst();
		} catch (Exception ex){

		}
		return false;
	}

	public boolean metGrades(){
		List<StudentGrade> modules = this.getModules();
		for (StudentGrade module: modules){
			if (module.getGrade() < getGradeNeeded()) return false;
		}
		return true;
	}

	public int getGradeNeeded(){
		Qualification qualification = this.getQualificationType();
		switch (qualification){
			case MPsy:
			case MSc:
			case MA:
			case MEng:
				return 40;
			// everything else returns 50
			default:
				return 50;
		}
	}

	public boolean metCredits(){
		return this.getCreditRequirements() == this.getCreditsTaken();
	}

	public int getCreditRequirements(){
		if (this.levelOfStudy == LevelOfStudy.P){
			// post grad
			return 180;
		}
		// undergrad
		return 120;
	}

	public int getCreditsTaken(){
		int creditsTaken = 0;
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
			PreparedStatement pstmt = con.prepareStatement("SELECT credits FROM StudentModule JOIN Module " +
																"ON Module.moduleCode = StudentModule.moduleCode\n " +
																"WHERE regNumber =? AND StudentModule.levelOfStudyTaken =?;");
			pstmt.setInt(1,this.getRegNumber());
			pstmt.setString(2,this.getLevelOfStudy().toString());
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()){
				creditsTaken += Integer.parseInt(rs.getString("credits"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return creditsTaken;
	}

	public List<InspectRegTableRow> getAllModulesTaken(){
		String query  = "SELECT StudentModule.moduleCode,StudentModule.grade,Module.credits,StudentModule.levelOfStudyTaken," +
				"Module.moduleName FROM StudentModule JOIN Module ON StudentModule.moduleCode = Module.moduleCode" +
				" WHERE StudentModule.regNumber = ?;";

		List<InspectRegTableRow> inspectRegTableRows = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1,this.getRegNumber());
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()){
				String moduleCode = rs.getString("moduleCode");
				String moduleName = rs.getString("moduleName");
				String grade = rs.getString("grade");
				int credits = rs.getInt("credits");
				LevelOfStudy levelOfStudyTaken = LevelOfStudy.valueOf(rs.getString("levelOfStudyTaken"));
				inspectRegTableRows.add(new InspectRegTableRow(moduleCode,moduleName,grade,credits,levelOfStudyTaken));
			}
			return inspectRegTableRows;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static boolean exist(String username){
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

			PreparedStatement pstmt = con.prepareStatement("SELECT * FROM Student " +
																"WHERE username=? ;");
			pstmt.setString(1,username);
			ResultSet rs = pstmt.executeQuery();
			return rs.isBeforeFirst();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public void graduate() throws GradeAttainmentConstraint, LevelOfStudyConstraint {
		new Graduation(this).graduate();
	}

	public void enroll(UniModule module){
		String values = this.getRegNumber() + "','" + module.getCode() + "','" + this.getLevelOfStudy().toString();
		String query = "INSERT INTO StudentModule (regNumber,moduleCode,levelOfStudyTaken) VALUES ('" + values + "');";
		DBController.executeCommand(query);
	}

	public void autoEnroll(){
		List<CompulsoryModule> compulsoryModules = new CompulsoryModule().getAll(this.degreeCode,this.levelOfStudy);
		for (CompulsoryModule compulsoryModule : compulsoryModules){
			String values = this.getRegNumber() + "','" + compulsoryModule.getModuleCode() + "','" + this.getLevelOfStudy().toString();
			String query = "INSERT INTO StudentModule (regNumber,moduleCode,levelOfStudyTaken) VALUES ('" + values + "');";
			DBController.executeCommand(query);
		}
	}

	public List<StudentGrade> getModules() {
		String query = "SELECT * FROM StudentModule WHERE regNumber = " + this.getRegNumber();
		List<StudentGrade> studentModuleGrades = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(this.url, this.user, this.password)) {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String moduleCode = rs.getString("moduleCode");
				String levelOfStudyTaken = rs.getString("levelOfStudyTaken");
				int grade = rs.getInt("grade");
				String resit = rs.getString("resit");
				studentModuleGrades.add(new StudentGrade(this.regNumber,moduleCode,this.getForename(),this.getSurname(),grade, Boolean.valueOf(resit),
						LevelOfStudy.valueOf(levelOfStudyTaken)));
			}
		}
		catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		return studentModuleGrades;
	}

	public void retake(){
		List<StudentGrade> grades = this.getModules();
		for (StudentGrade grade : grades){
			if (grade.getGrade() < this.getGradeNeeded()){
				grade.setResit(true);
				grade.setGrade(0);
			}
		}
	}

	public void removeAllModules(){
		String query = "DELETE FROM StudentModule WHERE regNumber =" + this.getRegNumber() + " ;";
		DBController.executeCommand(query);
	}

	/**
	 * Get the personal tutor(s) for a Student
	 * @return a Result set of the tutor(s) assigned to the student
	 * @throws SQLException
	 */
	//Get the personal tutor ID to then query for the email and name.
	//Set this at the property for the personal tutor name and email
	//so it can then be got and displayed on the student
	//welcome screen.
	public List<String> getPersonalTutor() throws SQLException {
		String query = "SELECT forename, surname, emailAddress FROM PersonalTutor JOIN Employee ON PersonalTutor.employeeNumber = " +
				"Employee.employeeNumber JOIN User ON User.username = Employee.username WHERE PersonalTutor.regNumber = " + this.regNumber;
		List<String> personalTutorInfo = new ArrayList<String>();
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
			PreparedStatement pstmt = con.prepareStatement("SELECT forename, surname, emailAddress FROM PersonalTutor " +
																"JOIN Employee ON PersonalTutor.employeeNumber = Employee.employeeNumber " +
																"JOIN User ON User.username = Employee.username\n " +
																"WHERE PersonalTutor.regNumber =?;");
			pstmt.setInt(1,this.getRegNumber());
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()){
				personalTutorInfo.add(rs.getString("forename"));
				personalTutorInfo.add(rs.getString("surname"));
				personalTutorInfo.add(rs.getString("emailAddress"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return personalTutorInfo;
	}

}