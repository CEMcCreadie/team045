package Views.Registrar;

import Controllers.Registrar.RegistrarWelcomeScreenController;
import Models.CourseStructure.LevelOfStudy;
import Models.Tables.Registrar.RegistrarTableModel;
import Models.UserAccounts.Employee.Registrar;
import Views.WelcomeScreen;

import javax.swing.*;

public class RegistrarWelcomeScreen extends WelcomeScreen {
    private JPanel mainPanel;
    private JComboBox periodOfStudyCombo;
    private JComboBox sntCourseCombo;
    private JPanel stntCoursePane;
    private JLabel stntCourseLbl;
    private JPanel periodOfStudyPane;
    private JLabel periodOfStudyLbl;
    private JTable viewStntTable;
    private JPanel viewStntButtPane;
    private JScrollPane viewStntScrollPane;
    private JButton delStntButt;
    private JButton applyStudentButt;
    private JButton inspectRegistrationButt;
    private JLabel welcomeLabel;
    private Registrar registrar;
    private RegistrarWelcomeScreenController controller;

    public RegistrarWelcomeScreen(Registrar registrar, RegistrarWelcomeScreenController controller){
        super();
        this.controller = controller;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.registrar = registrar;
        this.pack();

        welcomeLabel.setText("Welcome " + registrar.getForename() +" "+ registrar.getSurname()+", Logged in as registrar.");
        //Sets up the main table
        RegistrarTableModel viewStntTableModel = new RegistrarTableModel();
        viewStntTable.setModel(viewStntTableModel);
      
        delStntButt.addActionListener(e ->{
            int selRowIndex = viewStntTable.getSelectedRow();
            if (selRowIndex > -1){
                controller.unassignStudent(viewStntTableModel.getRow(selRowIndex));
            };
            this.update();
        });

        inspectRegistrationButt.addActionListener(e -> {
            int selRowIndex = viewStntTable.getSelectedRow();
            //Ensures that there is a row selected
            if (selRowIndex > -1) {
                controller.inspectStudentRegistration(viewStntTableModel.getRow(selRowIndex));
            }
            this.update();
        });

        applyStudentButt.addActionListener(e -> {
            //Ensures that there is a row selected
            int selRowIndex = viewStntTable.getSelectedRow();
            if (selRowIndex > -1){
                controller.assignStudent(viewStntTableModel.getRow(selRowIndex),
                        sntCourseCombo.getSelectedItem().toString(),
                        periodOfStudyCombo.getSelectedItem().toString());
            }
            this.update();
        });
        this.update();

    }

    public void update(){
        //Sets up the main table
        RegistrarTableModel viewStntTableModel = new RegistrarTableModel();
        viewStntTable.setModel(viewStntTableModel);

        //Sets the model for the ComboBoxes
        DefaultComboBoxModel courseComboModel = new DefaultComboBoxModel(registrar.getAllCourses());
        sntCourseCombo.setModel(courseComboModel);

        DefaultComboBoxModel levelComboModel = new DefaultComboBoxModel(LevelOfStudy.getAllLevelsOfStudies());
        periodOfStudyCombo.setModel(levelComboModel);
    }

    public static void main(String args[]){
        //Registrar registrar = new Registrar();
        //JFrame frame = new RegistrarWelcomeScreen(registrar);
        //frame.setVisible(true);
    }
}
