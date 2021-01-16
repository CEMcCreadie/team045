package Models.UserAccounts;

import Models.CourseStructure.Qualification;
import Models.UserAccounts.Employee.Administrator;
import Models.UserAccounts.Student.*;;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdministratorTest {

    private Administrator administrator;

    @BeforeEach
    public void init(){
        administrator = new Administrator("test","test","test","test",324);
    }

    @Test
    public void departmentTest(){
        administrator.addUniversityDepartment("ARTY","Art Department");
        administrator.removeUniversityDepartment("ARTY");
    }

    @Test
    public void addEmployeeTest(){
        // Obviously can't add yourself irl, but your'e not gonna be able to login if you dont exist
        administrator.addEmployee(administrator);
        administrator.removeEmployee(administrator.getEmployeeNumber());
    }

    @Test
    public void addModuleTest(){
        administrator.addModule("COM1009","Algorithms And Data Structures",5);
        administrator.removeModule("COM1009");
    }

    @Test
    public void degreeTest(){
        administrator.addDegree("COM060","Computer Science",3,false, Qualification.BEng);
        administrator.removeDegree("COM060");
    }
    @Test
    public void addRemoveStudent(){
        Student student = new Student("testy01","Mr","test","dsadas@gmail.com",101,"COMU01","ONE");
        for (int i = 0; i < 3; i++) {
            administrator.addStudent(student);
            administrator.removeStudent(student.getRegNumber());
        }
    }
}