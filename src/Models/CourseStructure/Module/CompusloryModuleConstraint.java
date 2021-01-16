package Models.CourseStructure.Module;

public class CompusloryModuleConstraint extends Exception {
    //Execption for CompulsoryModule
    @Override
    public void printStackTrace(){
        System.out.println("Cant remove Compulsory Modules");
    }
}
