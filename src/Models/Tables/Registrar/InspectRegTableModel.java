package Models.Tables.Registrar;

import Models.DatabaseBehaviours.DBController;
import Models.UserAccounts.Student.*;;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

// Todo rename class
public class InspectRegTableModel extends AbstractTableModel {
    private final List<InspectRegTableRow> rows;

    private String[] columnNames = new String[]{"Module Code","Module Name","Grade","Credits","Level Of Study Taken"};
    private final Class[] columnClass = new Class[]{String.class,String.class,String.class,Integer.class,String.class};

    public InspectRegTableModel(Student student){
        this.rows = student.getAllModulesTaken();
    }

    //@Override
    public String getColumnName(int column) {
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
        return rows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        InspectRegTableRow row = rows.get(rowIndex);
        switch (columnIndex){
            case 0:
               return row.getModuleCode();
            case 1:
                return row.getModuleName();
            case 2:
                return row.getGrade();
            case 3:
                return row.getCredits();
            case 4:
                return row.getLevelOfStudyTaken().toString();
            default:
                return null;
        }
    }
    public InspectRegTableRow getRow(int rowIndex){
        return rows.get(rowIndex);
    }
}
