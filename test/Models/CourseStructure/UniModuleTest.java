package Models.CourseStructure;

import Models.CourseStructure.Module.UniModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniModuleTest {

    private UniModule module;

    @BeforeEach
    public void init(){
        module = new UniModule("COM1009","Algorithm and Data Structures",20);
    }

    @Test
    public void addExistDeleteTest(){
        module.add();
        assertTrue(module.exists());
        module.remove();
        assertFalse(module.exists());
    }

}
