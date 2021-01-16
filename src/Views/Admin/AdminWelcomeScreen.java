package Views.Admin;


import Controllers.Admin.AdminController;
import Models.CourseStructure.*;
import Models.CourseStructure.Degree.Degree;
import Models.CourseStructure.Degree.DegreeDepartment;
import Models.CourseStructure.Module.CompulsoryModule;
import Models.CourseStructure.Module.TeachesModule;
import Models.CourseStructure.Module.UniModule;
import Models.Tables.Admin.RemoveSelectedUser;
import Models.Tables.CourseStructure.*;

import Models.Tables.Admin.UserTableModel;
import Models.UserAccounts.Employee.TeacherDetails;
import Models.UserAccounts.User.UserType;
import Views.WelcomeScreen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminWelcomeScreen extends WelcomeScreen {
    private JPanel mainPanel;
    private JTabbedPane tabbedPanel;
    private JPanel usersTab;
    private JLabel welcomeLabel;
    private JPanel userTabButtPanel;
    private JLabel newUserLabel;
    private JLabel userNameLbl;
    private JButton applyNewUserButt;
    private JButton removeSelectedUserButt;
    private JCheckBox yearInIndustryCheckBox;
    private JTextField deptCodeTxtField;
    private JScrollPane departmentScrollPane;
    private JPanel departmentMethodsScrollPane;
    private JLabel addDeptLbl;
    private JLabel deptNameLbl;
    private JTextField deptNameTxtField;
    private JButton addDeptButt;
    private JButton removeSelectDeptButt;
    private JLabel deptCodeLbl;
    private JScrollPane coursesTableScrollPane;
    private JPanel editCoursesPane;
    private JTable usersTable;
    private JTable modulesTable;
    private JTable departmentsTable;
    private JTable coursesTable;
    private JLabel addCourseLbl;
    private JLabel courseNameLbl;
    private JTextField courseNameTxt;
    private JLabel courseLengthLbl;
    private JTextField courseLengthTxtArea;
    private JLabel courseDegreeSigLbl;
    private JButton addCourseButt;
    private JButton removeSelectedCourseButt;
    private JLabel courseLeadDeptLbl;
    private JComboBox courseLeadDeptComboBox;
    private JTextField degreeCodeTxtField;
    private JScrollPane modulesScrollPane;
    private JPanel modulesAddPane;
    private JTextField moduleNameTxtField;
    private JTextField moduleCreditsTxtField;
    private JComboBox moduleLeadDeptComboBox;
    private JScrollPane userTableScrollPane;
    private JTextField userForeNameTxtField;
    private JTextField userSurNameTxtField;
    private JComboBox<UserType> userPrivilegeCombo;
    private JButton moduleAddButton;
    private JTextField moduleNumberTxtField;
    private JButton removeSelectedModuleButton;
    private JTextField qualificationTxtField;
    private JTextField passwordTxtField;
    private JComboBox compDegreeCodeCombo;
    private JComboBox compModuleCodeCombo;
    private JComboBox compLevelCombo;
    private JTable compulsoryModules;
    private JButton makeCompulsoryButton;
    private JButton removeCompulsoryButton;
    private JScrollPane compulsoryModulesTable;
    private JButton degreeDepoRemoveButton;
    private JComboBox degreeDepoDepoCodeCombo;
    private JComboBox degreeDepoDegreeCodeCombo;
    private JButton degreeDepoAddButton;
    private JTable degreeDepoTable;
    private JTable teachesModuleTable;
    private JComboBox teacherFullNameComboBox;
    private JButton addTeacherToModuleButton;
    private JButton removeTeacherFromModuleButton;
    private JTable personalTutorsTable;
    private JButton removeTeacherFromStudentButton;
    private JButton addTeacherToStudentButton;
    private JComboBox personalTutorComboBox;
    private AdminController controller;
    private TeachesModuleTableModel teachesModuleTableModel;
    private PersonalTutorTableModel personalTutorTableModel;

    public AdminWelcomeScreen(AdminController controller){
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.controller = controller;
        this.pack();

        welcomeLabel.setText("Welcome " + controller.getAdminForename() + " " + controller.getAdminSurname() +
                ", Logged in as Administrator");

        addDeptButt.addActionListener(e -> {
            this.controller.addDepartment(deptCodeTxtField.getText(), deptNameTxtField.getText());
            deptCodeTxtField.setText("");
            deptNameTxtField.setText("");
            this.update();
        });

        addCourseButt.addActionListener(e -> {
            this.controller.addCourse((String)courseLeadDeptComboBox.getSelectedItem(),degreeCodeTxtField.getText(),courseNameTxt.getText(),Integer.parseInt(courseLengthTxtArea.getText()),yearInIndustryCheckBox.isSelected(),
            qualificationTxtField.getText());
            this.degreeCodeTxtField.setText("");
            this.courseNameTxt.setText("");
            this.courseLengthTxtArea.setText("");
            this.update();
        });

        applyNewUserButt.addActionListener(e -> {
            this.controller.addUser((UserType)userPrivilegeCombo.getSelectedItem(),userForeNameTxtField.getText(),userSurNameTxtField.getText(),passwordTxtField.getText());
            userForeNameTxtField.setText("");
            userSurNameTxtField.setText("");
            this.update();
        });

        moduleAddButton.addActionListener(e -> {
            this.controller.addModule(moduleNumberTxtField.getText(),moduleNameTxtField.getText(),Integer.parseInt(moduleCreditsTxtField.getText()),(String)moduleLeadDeptComboBox.getSelectedItem());
            moduleNumberTxtField.setText("");
            moduleNameTxtField.setText("");
            moduleCreditsTxtField.setText("");
            this.update();
        });

        makeCompulsoryButton.addActionListener(e -> {
            this.controller.addCompulsoryModule((String)compDegreeCodeCombo.getSelectedItem(),(String)compModuleCodeCombo.getSelectedItem(),(LevelOfStudy)compLevelCombo.getSelectedItem());
            this.update();
        });

        degreeDepoAddButton.addActionListener(e -> {
            this.controller.addDegreeDepartment((String)degreeDepoDepoCodeCombo.getSelectedItem(),(String)degreeDepoDegreeCodeCombo.getSelectedItem());
            this.update();
        });

        addTeacherToModuleButton.addActionListener(e -> {
            this.controller.addTeachesModule((String)teacherFullNameComboBox.getSelectedItem(),(TeachesModule)this.teachesModuleTableModel.getRow(teachesModuleTable.getSelectedRow()));
            this.update();
        });

        addTeacherToStudentButton.addActionListener(e -> {
            this.controller.addPersonalTutor((String)personalTutorComboBox.getSelectedItem(),(PersonalTutor)this.personalTutorTableModel.getRow(personalTutorsTable.getSelectedRow()));
            this.update();
        });

        removeSelectDeptButt.addActionListener(new RemoveCourseStructure(this,departmentsTable,new DepartmentsTableModel(new Department())));
        removeSelectedCourseButt.addActionListener(new RemoveCourseStructure(this,coursesTable,new CourseTableModel(new Degree())));
        removeSelectedModuleButton.addActionListener(new RemoveCourseStructure(this,modulesTable,new ModulesTableModel(new UniModule())));
        removeCompulsoryButton.addActionListener(new RemoveCourseStructure(this,compulsoryModules,new CompulsoryModulesTableModel(new CompulsoryModule())));
        degreeDepoRemoveButton.addActionListener(new RemoveCourseStructure(this,degreeDepoTable,new DegreeDepartmentTableModel(new DegreeDepartment())));
        removeSelectedUserButt.addActionListener(new RemoveSelectedUser(this,usersTable,new UserTableModel()));
        removeTeacherFromModuleButton.addActionListener(new RemoveCourseStructure(this,teachesModuleTable,new TeachesModuleTableModel(new TeachesModule())));
        removeTeacherFromStudentButton.addActionListener(new RemoveCourseStructure(this,personalTutorsTable,new PersonalTutorTableModel(new PersonalTutor())));
        this.update();
    }

    private void setUserPrivilegeComboBox(){
        DefaultComboBoxModel<UserType> model = new DefaultComboBoxModel(UserType.getAllUserTypes());
        userPrivilegeCombo.setModel(model);
    }

    private void setLeadDeptComboBox() {
        List<Department> departments = new Department().getAll();
        List<String> departmentNames = new ArrayList<>();
        for (Department department : departments) {
            departmentNames.add(department.getDepartmentName());
        }
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel(departmentNames.toArray());
        moduleLeadDeptComboBox.setModel(model);
        courseLeadDeptComboBox.setModel(model);
    }

    private void setDegreeDepoDepoCodeCombo(){
        DefaultComboBoxModel<UserType> model = new DefaultComboBoxModel(Department.getAllDepartmentCodes());
        degreeDepoDepoCodeCombo.setModel(model);
    }

    private void setDegreeDepoDegreeCodeCombo(){
        DefaultComboBoxModel<UserType> model = new DefaultComboBoxModel(Degree.getAllDegreeCodes());
        degreeDepoDegreeCodeCombo.setModel(model);
    }

    public void update(){
        this.updateModulePane();
        this.updateDepartmentPane();
        this.updateCoursePane();
        this.updateUserPane();
        this.updateCompulsoryModulePane();
        this.updateDegreeDepartmentPane();
        this.updateTeachesModulePane();
        this.updatePersonalTutorPane();
    }

    private void updateDegreeDepartmentPane(){
        DegreeDepartmentTableModel degreeDepartmentTableModel = new DegreeDepartmentTableModel(new DegreeDepartment());
        degreeDepoTable.setModel(degreeDepartmentTableModel);
        this.setDegreeDepoDepoCodeCombo();
        this.setDegreeDepoDegreeCodeCombo();
        RemoveCourseStructure rcs = (RemoveCourseStructure)degreeDepoRemoveButton.getActionListeners()[0];
        rcs.updateTableModel(degreeDepartmentTableModel);
    }

    private void updateCompulsoryModulePane(){
        CompulsoryModulesTableModel compulsoryModulesTableModel = new CompulsoryModulesTableModel(new CompulsoryModule());
        compulsoryModules.setModel(compulsoryModulesTableModel);
        this.setCompDegreeCodeCombo();
        this.setCompModuleCodeCombo();
        this.setCompLevelCombo();
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeCompulsoryButton.getActionListeners()[0];
        rcs.updateTableModel(compulsoryModulesTableModel);
    }

    private void setCompModuleCodeCombo(){
        String[] moduleCodes = UniModule.getAllModuleCodes();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(moduleCodes);
        compModuleCodeCombo.setModel(model);
    }

    private void setCompLevelCombo(){
        DefaultComboBoxModel model = new DefaultComboBoxModel(LevelOfStudy.getAllLevelsOfStudies());
        compLevelCombo.setModel(model);
    }

    private void setTeacherFullNameComboBox(){
        DefaultComboBoxModel model = new DefaultComboBoxModel(TeacherDetails.getAllTeachersNamesAndNumbers());
        teacherFullNameComboBox.setModel(model);
        personalTutorComboBox.setModel(model);
    }

    private void setCompDegreeCodeCombo(){
        String[] degreeCodes = Degree.getAllDegreeCodes();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(degreeCodes);
        compDegreeCodeCombo.setModel(model);
    }

    private void updateModulePane(){
        ModulesTableModel modulesModel = new ModulesTableModel(new UniModule());
        modulesTable.setModel(modulesModel);
        // Update the table model the ActionListenerUses
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeSelectedModuleButton.getActionListeners()[0];
        rcs.updateTableModel(modulesModel);
        this.setLeadDeptComboBox();
    }

    private void updateDepartmentPane(){
        DepartmentsTableModel departmentsModel = new DepartmentsTableModel(new Department());
        departmentsTable.setModel(departmentsModel);
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeSelectDeptButt.getActionListeners()[0];
        rcs.updateTableModel(departmentsModel);
    }

    private void updateCoursePane(){
        CourseTableModel coursesModel = new CourseTableModel(new Degree());
        coursesTable.setModel(coursesModel);
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeSelectedCourseButt.getActionListeners()[0];
        rcs.updateTableModel(coursesModel);
    }

    private void updateUserPane() {
        UserTableModel userTableModel = new UserTableModel();
        usersTable.setModel(userTableModel);
        RemoveSelectedUser rcu = (RemoveSelectedUser)removeSelectedUserButt.getActionListeners()[0];
        rcu.updateTableModel(userTableModel);
        this.setUserPrivilegeComboBox();
    }

    private void updateTeachesModulePane() {
        this.teachesModuleTableModel = new TeachesModuleTableModel(new TeachesModule());
        teachesModuleTable.setModel(this.teachesModuleTableModel);
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeTeacherFromModuleButton.getActionListeners()[0];
        rcs.updateTableModel(teachesModuleTableModel);
        this.setTeacherFullNameComboBox();
    }

    private void updatePersonalTutorPane(){
        this.personalTutorTableModel = new PersonalTutorTableModel(new PersonalTutor());
        personalTutorsTable.setModel(this.personalTutorTableModel);
        RemoveCourseStructure rcs = (RemoveCourseStructure)removeTeacherFromStudentButton.getActionListeners()[0];
        rcs.updateTableModel(personalTutorTableModel);
        this.setTeacherFullNameComboBox();
    }

}
