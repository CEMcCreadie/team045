package Controllers.Teacher;

import Models.Graduation.GradeAttainmentConstraint;
import Models.Graduation.LevelOfStudyConstraint;
import Models.UserAccounts.Student.InsufficientCreditEnrollment;
import Models.UserAccounts.Student.InsufficientGradeAttainment;
import Models.UserAccounts.Student.*;;
import Views.Teacher.TeacherInspect;

public class InspectTeacherController{
    private TeacherInspect inspectionFrame;

    public InspectTeacherController(Student student){
        this.inspectionFrame = new TeacherInspect(student,this);
        inspectionFrame.setVisible(true);
    }

    public void progressStudent(Student student) {
        try {
            inspectionFrame.errLabel.setText("");
            student.updateLevelOfStudy();
        } catch (InsufficientCreditEnrollment ex){
            inspectionFrame.errLabel.setText("Insufficient Module Credits");
        } catch (InsufficientGradeAttainment ex){
            inspectionFrame.errLabel.setText("Insufficient Grades Attained");
        } catch (TooManyResits ex){
            inspectionFrame.errLabel.setText("Student has failed resat Level, they can now never progress," +
                    "modules have promptly deleted");
        }
    }

    public void retake(Student student){
        student.retake();
    }

    public void graduate(Student student){
        try {
            student.graduate();
        } catch (LevelOfStudyConstraint | GradeAttainmentConstraint ex ){
            inspectionFrame.errLabel.setText("Conditions not met");
        }
    }
}
