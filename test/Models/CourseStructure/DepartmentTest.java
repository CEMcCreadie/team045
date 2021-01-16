package Models.CourseStructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepartmentTest {

    private Department department;

    @BeforeEach
    public void init(){
        department = new Department("ELF","é o Nome do Meu Cão");
    }

    @Test
    public void addExistDeleteTest(){
        department.add();
        assertEquals(true,department.exists());
        department.remove();
        assertEquals(false,department.exists());
    }

}
