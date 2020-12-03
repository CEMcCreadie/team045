package Views.Student;

import Models.Tables.Student.StudentModuleTable;
import Models.UserAccounts.Student.Student;
import Views.WelcomeScreen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class StudentWelcomeScreen extends WelcomeScreen {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JScrollPane modulesPane;
    private JScrollPane gradesPane;
    private JTable gradesTable;
    private JTable modulesTable;
    private JLabel welcomeLabel;
    private JLabel personalTutorLabel;
    private JButton logOutButt;
    private Student student;

    /**
     *
     * @param gradeColumns - Columns for the grades table
     */
    public StudentWelcomeScreen(Student student,Object gradeColumns[]) {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.student = student;
        this.pack();

        //TODO: interface the moduleColumns and gradeColumns. Need to discuss with callum and salva.
        //instantiating table on model tab
        StudentModuleTable moduleModel = new StudentModuleTable(this.student);
        modulesTable.setModel(moduleModel);
        //instantiating table on grades tab
        DefaultTableModel gradeModel = new DefaultTableModel(gradeColumns, 0);
        gradesTable.setModel(gradeModel);
        //Instantiates student object using the login details
        //runs methods that update the welcome labels
        displayTutorLabel();
        displayWelcomeLabel();
    }


    private void displayWelcomeLabel() {
        welcomeLabel.setText("Welcome "+ student.getForename() + " Logged in as Student ");
    }

    private void displayTutorLabel() {
        try {
            List<String> tutorDetails = student.getPersonalTutor();
            System.out.print(tutorDetails);
            personalTutorLabel.setText("PERSONAL TUTOR NAME: " + tutorDetails.get(0) +
                     " " +tutorDetails.get(1) + " EMAIL: " + tutorDetails.get(2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
