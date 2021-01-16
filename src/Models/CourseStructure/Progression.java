package Models.CourseStructure;
import Models.DatabaseBehaviours.UserManipulator;
import Models.UserAccounts.Student.*;;

public class Progression {

    private static final int UNDERGRAD_CREDITS = 120;
    private static final int POSTGRAD_CREDITS = 180;

    private static final float LEVELS_ONE_TO_THREE_PASS = 39.5F;
    private static final float LEVEL_FOUR_PASS = 49.5F;

    private Student student;
    private UserManipulator studentDB;
}

