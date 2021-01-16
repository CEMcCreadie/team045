package Models.CourseStructure;

public enum LevelOfStudy {

    ONE,
    TWO,
    THREE,
    P;

    public static LevelOfStudy[] getAllLevelsOfStudies(){
        LevelOfStudy[] x = {ONE,TWO,THREE,P};
        return x;
    }

    public static LevelOfStudy getNext(LevelOfStudy levelOfStudy){
        switch (levelOfStudy){
            case ONE:
                return TWO;
            case TWO:
                return THREE;
            case THREE:
                return P;
            default:
                return levelOfStudy;
        }
    }

}

