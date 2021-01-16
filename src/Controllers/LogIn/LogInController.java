package Controllers.LogIn;


import Controllers.Admin.AdminController;
import Controllers.Registrar.RegistrarWelcomeScreenController;
import Controllers.Teacher.TeacherWelcomeScreenController;
import Models.Authentication.Login;
import Models.UserAccounts.Employee.Administrator;
import Models.UserAccounts.Employee.Registrar;
import Models.UserAccounts.Employee.Teacher;
import Models.UserAccounts.Student.*;;
import Models.UserAccounts.User.UserAccountBuilder;
import Views.Admin.AdminWelcomeScreen;
import Views.LogIn;
import Views.Registrar.RegistrarWelcomeScreen;
import Views.Student.StudentWelcomeScreen;
import Views.Teacher.TeacherWelcomeScreen;

import javax.swing.*;

/**
 * Action listeners are added in login class and methods are called here
 * an instance of this is created in login
 *
 */
public class LogInController {

    private LogIn loginFrame;

    public LogInController() {
        this.loginFrame = new LogIn("University Management System", this);
        loginFrame.setVisible(true);
    }

    public void loginButtonPress(String username, char[] password) {
        if (Login.loginAuthenticated(username, String.valueOf(password))) {
            // if password matched instantiate new view
            JFrame frame = this.getUserFrame(username);
            frame.setVisible(true);
            loginFrame.dispose();
        }
        else{
        loginFrame.incorrectPassword();
        }
    }


    public JFrame getUserFrame(String username) {
        UserAccountBuilder builder = new UserAccountBuilder(username);
        if (Student.exist(username)) {
            Student student = builder.studentBuilder();
            JFrame frame = new StudentWelcomeScreen(student, new Object[]{"Placeholder", "for", "grades"});
            return frame;
        }
        switch (builder.getEmployeeRole()) {
            case ADMIN:
                Administrator administrator = builder.employeeBuilder(new Administrator());
                AdminController adminController = new AdminController(administrator);
                return new AdminWelcomeScreen(adminController);
            case TEACHER:
                Teacher teacher = builder.employeeBuilder(new Teacher());
                return new TeacherWelcomeScreen(teacher, new TeacherWelcomeScreenController(teacher));
            case REGISTRAR:
                Registrar registrar = builder.employeeBuilder(new Registrar());
                return new RegistrarWelcomeScreen(registrar, new RegistrarWelcomeScreenController(registrar));
            default:
                return null;
        }
    }
    public static void main(String[] args) {
        LogInController logInController = new LogInController();
    }

}
