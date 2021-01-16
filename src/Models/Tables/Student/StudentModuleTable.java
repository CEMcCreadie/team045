package Models.Tables.Student;

import Models.Tables.StudentGrade;

import javax.swing.table.AbstractTableModel;

import Models.UserAccounts.Student.*;;

import java.util.List;

public class StudentModuleTable extends AbstractTableModel {

    private final List<StudentGrade> studentGrades;

    private final String[] columnNames = new String[] {"Module Code","Level Taken","Grade","Resit"};
    private final Class[] columnClass = new Class[] {String.class,String.class,Integer.class,Integer.class};

    public StudentModuleTable(Student student){
        this.studentGrades = student.getModules();
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public int getRowCount()
    {
        return studentGrades.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        StudentGrade row = studentGrades.get(rowIndex);
        switch (columnIndex){
            case 0:
                return row.getModuleCode();
            case 1:
                return row.getLevelOfStudyTaken();
            case 2:
                return row.getGrade();
            case 3:
                return row.getResit();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

}

