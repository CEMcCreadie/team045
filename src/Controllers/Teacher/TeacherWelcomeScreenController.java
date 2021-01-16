package Controllers.Teacher;

import Models.Tables.StudentGrade;
import Models.UserAccounts.Student.Student;
import Models.UserAccounts.Employee.Teacher;
import Models.UserAccounts.User.UserAccountBuilder;
import Views.Registrar.RegistrarWelcomeScreen;

public class TeacherWelcomeScreenController {

    private RegistrarWelcomeScreen registrarFrame;
    private Teacher teacher;

    public TeacherWelcomeScreenController(Teacher teacher ){
        this.teacher = teacher;
    }

    public void inspectStudentRegistration(StudentGrade row){
        Student student = UserAccountBuilder.studentBuilderFromReg(row.getRegNumber());
        if(student.getDegreeCode() != null && student.getLevelOfStudy() != null) {
            new InspectTeacherController(student);
        }
    }

    public static void main(String args[]){
        new TeacherWelcomeScreenController(new UserAccountBuilder("Teacher5").employeeBuilder(new Teacher()));
    }
}
