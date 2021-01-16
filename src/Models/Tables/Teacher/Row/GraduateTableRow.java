package Models.Tables.Teacher.Row;

public class GraduateTableRow {

    private int regNumber;
    private String Achievement;
    private String Qualification;



    public GraduateTableRow(int regNumber, String achievement, String qualification) {
        this.regNumber = regNumber;
        Achievement = achievement;
        Qualification = qualification;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public String getAchievement() {
        return Achievement;
    }

    public String getQualification() {
        return Qualification;
    }
}
