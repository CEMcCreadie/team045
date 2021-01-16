package Models.Graduation;

import Models.CourseStructure.LevelOfStudy;
import Models.CourseStructure.Qualification;
import Models.DatabaseBehaviours.DBController;
import Models.Tables.StudentGrade;
import Models.UserAccounts.Student.Student;

import java.util.List;

public class Graduation {

    private Student student;
    private List<StudentGrade> grades;
    private Qualification qualification;

    public Graduation(Student student) {
        this.student = student;
        this.grades = student.getModules();
        this.qualification = student.getQualificationType();
    }

    private int calculateYearScore(LevelOfStudy year){
        int gradeCount = 0;
        int modulesTookCount = 0;
        for(StudentGrade grade: grades){
            if(LevelOfStudy.valueOf(grade.getLevelOfStudyTaken()) == year){
               gradeCount += grade.getGrade();
               modulesTookCount++;
            }
        }
        int averageGrade = gradeCount/modulesTookCount;
        return averageGrade;
    }

    private int calculateFinalScore(){
        // fair to assume student graduates at the latest level of their study.
       if (student.getLevelOfStudy() == LevelOfStudy.THREE){
            double secondYearGrade = 0.33 * calculateYearScore(LevelOfStudy.TWO);
            double thirdYearGrade = 0.66 * calculateYearScore(LevelOfStudy.THREE);
            return (int)(secondYearGrade + thirdYearGrade);
       } else {
           double secondYearGrade = 0.2 * calculateYearScore(LevelOfStudy.TWO);
           double thirdYearGrade = 0.4 * calculateYearScore(LevelOfStudy.THREE);
           double fourthYearGrade = 0.4 * calculateYearScore(LevelOfStudy.P);
           return (int)(secondYearGrade + thirdYearGrade + fourthYearGrade);
       }
    }

    private DegreeClassification classifyDegree() throws GradeAttainmentConstraint{
       switch (student.getQualificationType()){
           case BPsy:
           case BSc:
           case BA:
           case BEng:
               return classifyBachelors();
           case MSc:
           case MPsy:
               return classifyOneYearMasters();
           case MEng:
               return classifyMasters();
       }
       return null;
    }

    public void graduate() throws GradeAttainmentConstraint,LevelOfStudyConstraint{
        if (student.getLevelOfStudy() == LevelOfStudy.TWO || student.getLevelOfStudy() == LevelOfStudy.ONE) throw new LevelOfStudyConstraint();
        DegreeClassification classification = classifyDegree();
        insertIntoDatabase(classification);
    }

    private void insertIntoDatabase(DegreeClassification classification){
        String values = student.getRegNumber() + "','" + classification.toString() + "','" + this.qualification.toString();
        String query = "INSERT INTO Graduates VALUES ('"+values+"') ;";
        DBController.executeCommand(query);
        student.removeAllModules();
    }

    private DegreeClassification classifyBachelors() throws  GradeAttainmentConstraint{
        int score = calculateFinalScore();
        if (score < 40) {
            throw new GradeAttainmentConstraint(score);
        } else if (score < 45){
            return DegreeClassification.PASS_NON_HONOURS;
        } else if (score < 50){
            return DegreeClassification.THIRD_CLASS;
        } else if (score < 60){
           return DegreeClassification.LOWER_SECOND;
        } else if (score < 70){
            return DegreeClassification.UPPER_SECOND;
        } else {
            return DegreeClassification.FIRST_CLASS;
        }
    }

    private DegreeClassification classifyOneYearMasters() throws GradeAttainmentConstraint{
        int score = calculateFinalScore();
        if (score < 50){
            throw new GradeAttainmentConstraint(score);
        } else if (score < 60){
            return DegreeClassification.PASS;
        } else if (score < 70){
            return DegreeClassification.MERIT;
        } else {
            return DegreeClassification.DISTINCTION;
        }
    }

    private DegreeClassification classifyMasters() throws GradeAttainmentConstraint{
        if (this.calculateYearScore(LevelOfStudy.P) < 50){
            student.setLevelOfStudy(LevelOfStudy.THREE);
            this.lowerQualificationType();
            return classifyBachelors();
        }
        int score = calculateFinalScore();
        if (score < 50){
            throw new GradeAttainmentConstraint(score);
        } else if (score < 60){
            return DegreeClassification.LOWER_SECOND;
        } else if (score < 70){
            return DegreeClassification.UPPER_SECOND;
        } else {
            return DegreeClassification.FIRST_CLASS;
        }
    }

    private void lowerQualificationType(){
        switch (this.qualification){
            case MSc:
                this.qualification = Qualification.BSc;
                return;
            case MA:
                this.qualification = Qualification.BA;
                return;
            case MPsy:
                this.qualification = Qualification.BPsy;
                return;
            case MEng:
                this.qualification = Qualification.BEng;
                return;
        }
    }


}
