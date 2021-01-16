package Models.UserAccounts;

import Models.UserAccounts.Student.*;;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    private Student student;

    @BeforeEach
    public void init(){
        student = new Student("Lembrei","test","test","test",12345,"ENG040","TWO");
    }

    @Test
    public void getCreditsTakenTest() {
        assertEquals(40,student.getCreditsTaken());
    }

}
