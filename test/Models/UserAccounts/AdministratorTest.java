package Models.UserAccounts;

import Models.UserAccounts.Administrator;
import Models.UserAccounts.UserType;

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
        administrator.addUniversityDepartment("ART","Art Department");
        administrator.removeUniversityDepartment("ART");
    }

    @Test
    public void addEmployeeTest(){
        // Obviously can't add yourself irl, but your'e not gonna be able to login if you dont exist
        administrator.addEmployee(administrator, UserType.ADMIN);
        administrator.removeEmployee(administrator.getEmployeeNumber());
    }

    @Test
    public void addModuleTest(){
        administrator.addModule("COM2004","Data Driven Computing",5,2);
        administrator.removeModule("COM2004");
    }

    @Test
    public void degreeTest(){
        administrator.addDegree("COM060","Computer Science",3,false);
        administrator.removeDegree("COM060");
    }

}