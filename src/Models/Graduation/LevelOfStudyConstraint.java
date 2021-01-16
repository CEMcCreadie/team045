package Models.Graduation;

public class LevelOfStudyConstraint extends Exception {

    @Override
    public void printStackTrace(){
        System.out.println("The Student has not completed sufficient Levels of Study to graduate");
    }
}
