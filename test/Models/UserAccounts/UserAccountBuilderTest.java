package Models.UserAccounts;

import Models.UserAccounts.Employee.Teacher;
import Models.UserAccounts.Student.*;;
import Models.UserAccounts.User.UserAccountBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountBuilderTest {

    @Test
    public void studentBuilderTest(){
        UserAccountBuilder builder = new UserAccountBuilder("Lembrei");
        Student student = builder.studentBuilder();
        Student student1 = new Student("Lembrei","Bobby","Williams","bwilliams4@sheffield.ac.uk",
                12345,null,"ONE");
        assertEquals(student.getEmailAddress(),student1.getEmailAddress());
    }

    @Test
    public void teacherBuildTest(){
        UserAccountBuilder builder = new UserAccountBuilder("Eu");
        Teacher teacher = builder.employeeBuilder(new Teacher());
        Teacher teacher1 = new Teacher("Eu","Callum","McCreadie","eu@sheffield.ac.uk",1);
        assertTrue(teacher1.equals(teacher));
    }



}