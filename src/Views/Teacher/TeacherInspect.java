package Views.Teacher;

import Controllers.Registrar.InspectRegistrationController;
import Controllers.Teacher.InspectTeacherController;
import Controllers.Teacher.InspectTeacherController;
import Models.Tables.Registrar.InspectRegTableModel;
import Models.UserAccounts.Student.Student;
import Views.WelcomeScreen;

import javax.swing.*;

public class TeacherInspect extends WelcomeScreen {

    private JPanel buttonPanel;
    private JProgressBar progressBar1;
    private JTable studentModulesTable;
    private JLabel studentInfoLabel;
    private JButton progressStudentToNextButton;
    private JButton retakeLevelButton;
    private JPanel mainPanel;
    public JLabel errLabel;
    private JButton graduateStudentButton;
    private InspectTeacherController controller;
    private final Student student;
    private InspectRegTableModel inspectRegModel;

    public TeacherInspect(Student student, InspectTeacherController controller) {
        super();
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.student = student;
        this.pack();

        //sets the students info label
        studentInfoLabel.setText("Showing Registration for " + student.getForename() + " " + student.getSurname() +
                " Reg: " + student.getRegNumber() + " Year: " + student.getLevelOfStudy());

        retakeLevelButton.addActionListener(e -> {
            this.controller.retake(student);
            this.update();
        });

        progressStudentToNextButton.addActionListener(e -> {
            this.controller.progressStudent(student);
            this.update();
        });

        this.update();

        graduateStudentButton.addActionListener(e -> {
            controller.graduate(student);
            this.update();
        });

    }

    public void update(){
        this.inspectRegModel = new InspectRegTableModel(student);
        studentModulesTable.setModel(inspectRegModel);
        int creditsTaken = student.getCreditsTaken();
        int creditsNeeded = student.getCreditRequirements();
        progressBar1.setMaximum(creditsNeeded);
        progressBar1.setValue(creditsTaken);
    }

}
