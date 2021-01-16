package Models.Graduation;

public class GradeAttainmentConstraint extends Exception {

    private int score;

    public GradeAttainmentConstraint(int score) {this.score = score;}

    @Override
    public void printStackTrace(){
        System.out.println("Student does not have high enough grade average to graduate, their grade average is " + score);
    }

}
