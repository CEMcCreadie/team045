package Models.UserAccounts.Employee;

import Models.CourseStructure.LevelOfStudy;
import Models.DatabaseBehaviours.DBController;
import Models.Tables.StudentGrade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Models.UserAccounts.Student.Student;
import Models.UserAccounts.User.UserType;

public class Teacher extends Employee {

	//for adding a new user to the DB
	public Teacher (String forename,String surname) {
		super(forename, surname);
	}
	//Dummy Class
	public Teacher(){
		super();
	}

  	public Teacher (String username,String forename,String surname,String emailAddress,int employeeNumber){
	   super(username, forename, surname, emailAddress, employeeNumber);
  	}

  	@Override
	public UserType getRole(){
		return UserType.TEACHER;
	}

	public List<StudentGrade> getGradesOfStudents() {
		List<StudentGrade> studentGrades = new ArrayList<>();
		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

			PreparedStatement pstmt = con.prepareStatement("SELECT StudentModule.regNumber, StudentModule.moduleCode, forename, surname, grade, resit, levelOfStudyTaken FROM StudentModule " +
																"INNER JOIN TeachesModule ON StudentModule.moduleCode = TeachesModule.moduleCode " +
																"INNER JOIN Student ON Student.regNumber = StudentModule.regNumber " +
																"INNER JOIN User ON User.username = Student.username\n " +
																"WHERE TeachesModule.employeeNumber =?;");
			pstmt.setInt(1,this.getEmployeeNumber());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				int regNumber = rs.getInt("regNumber");
				String moduleCode = rs.getString("moduleCode");
				String forename = rs.getString("forename");
				String surname = rs.getString("surname");
				int grade = rs.getInt("grade");
				String resit = rs.getString("resit");
				String levelOfStudy = rs.getString("levelOfStudyTaken");
				studentGrades.add(new StudentGrade(regNumber,moduleCode,forename,surname,grade,Boolean.valueOf(resit),
						LevelOfStudy.valueOf(levelOfStudy)));
			}
			// Count should never be greater than one, I believe
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return studentGrades;
	}


	/**
	 * Get the regNumbers for the tutees of a especific Teacher
	 * @return a Result set with all regNumbers from the teacher's tutees
	 * @throws SQLException
	 */
	public List<Student> getTutees() throws SQLException {
		String query = "SELECT regNumber \n"+
					   "FROM PersonalTutor \n" +
					   "WHERE employeeNumber='"+this.getEmployeeNumber()+"';";
		List<Integer> tuteesRegNumbers = new ArrayList<>();
		List<Student> tutees = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){

			PreparedStatement pstmt = con.prepareStatement("SELECT regNumber \n"+
																"FROM PersonalTutor \n" +
																"WHERE employeeNumber=?;");
			pstmt.setInt(1,this.getEmployeeNumber());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int regNumber = rs.getInt("regNumber");
				tuteesRegNumbers.add(regNumber);
			}
		  }
		 catch (SQLException e) {
			 e.printStackTrace();
		}
		for (int tuteeRegNumber : tuteesRegNumbers) {
			try (Connection con = DriverManager.getConnection(DBController.url, DBController.user, DBController.password)) {

				PreparedStatement pstmt = con.prepareStatement("SELECT User.username, User.forename, User.surname, User.emailAddress, Student.regNumber, Student.degreeCode, Student.levelOfStudy \n" +
																	"FROM Student INNER JOIN User ON Student.username = User.username  \n" +
																	"WHERE Student.regNumber=?;");
				pstmt.setInt(1, tuteeRegNumber);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					String username = rs.getString("username");
					String forename = rs.getString("forename");
					String surname = rs.getString("surname");
					String emailAddress = rs.getString("emailAddress");
					int regNumber = rs.getInt("regNumber");
					String degreeCode = rs.getString("degreeCode");
					String levelOfStudy = rs.getString("levelOfStudy");
					tutees.add(new Student(username,forename,surname,emailAddress,regNumber, degreeCode, levelOfStudy));

				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tutees;
	}

	/**
	 * Get the Module Code for the Module taught by a especific Teacher
	 * @return a Result set with all regNumbers from the teacher's tutees
	 * @throws SQLException
	 */

	public List<String> getModulesTaught() throws SQLException{
		 String query = "SELECT moduleCode \n"+
						"FROM TeachesModule \n" +
						"WHERE employeeNumber='"+this.getEmployeeNumber()+"';";
		List<String> modulesCodes = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)) {

			PreparedStatement pstmt = con.prepareStatement("SELECT moduleCode \n"+
																"FROM TeachesModule \n" +
																"WHERE employeeNumber=?;");
			pstmt.setInt(1, this.getEmployeeNumber());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String moduleCode = rs.getString("moduleCode");
				modulesCodes.add(moduleCode);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return modulesCodes;
	}

}


