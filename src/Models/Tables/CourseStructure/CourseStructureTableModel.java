package Models.Tables.CourseStructure;

import Models.CourseStructure.CourseStructure;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class CourseStructureTableModel extends AbstractTableModel {

    protected List<? extends CourseStructure> courseStructureList = new ArrayList<>();

    protected final String[] columnNames;
    protected final Class[] columnClass;


    public <T extends CourseStructure> CourseStructureTableModel(T t) {
        this.courseStructureList = t.getAll();
        this.columnNames = t.getVariableNames();
        this.columnClass = t.getVariableClass();
    }

    public void removeRow(int[] rowIndex) {
        List<CourseStructure> courseStructuresToRemove = new ArrayList<>();
        for (int i: rowIndex){
            courseStructuresToRemove.add(this.courseStructureList.get(i));
        }
        // we couldn't put this in the for loop above because the index would be wrong after first delete
        List<Integer> rowIndexList  = Arrays.stream( rowIndex ).boxed().collect( Collectors.toList() );
        Collections.reverse(rowIndexList);
        for (Integer i : rowIndexList){
            this.courseStructureList.remove(i);
        }
        // Remove elements from Database
        for (CourseStructure courseStructureElement: courseStructuresToRemove){
            courseStructureElement.remove();
        }
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
        return courseStructureList.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ArrayList variableList = courseStructureList.get(rowIndex).getVariables();
        return variableList.get(columnIndex);
    }

    // This can be Overridden again
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    public CourseStructure getRow(int rowIndex){
        return this.courseStructureList.get(rowIndex);
    }

}
