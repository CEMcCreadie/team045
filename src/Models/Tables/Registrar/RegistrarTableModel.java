package Models.Tables.Registrar;

import Models.UserAccounts.Employee.Registrar;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RegistrarTableModel extends AbstractTableModel {

    private final List<RegistrarTableRow> rows;
    private Registrar registrar = new Registrar();
    private String[] columnNames = new String[]{"Reg Number","Username","Degree Code","Level Of Study","Forename","Surname","Email Address"};
    private final Class[] columnClass = new Class[]{Integer.class,String.class,String.class,String.class,String.class,String.class,String.class};

    public RegistrarTableModel(){
        this.rows = registrar.getAllStudents();
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
        RegistrarTableRow row = rows.get(rowIndex);
        switch (columnIndex){
            case 0:
                return row.getRegNumber();
            case 1:
                return row.getUserName();
            case 2:
                return row.getDegreeCode();
            case 3:
                return row.getLevelOfStudy();
            case 4:
                return row.getForeName();
            case 5:
                return row.getSurName();
            case 6:
                return row.getEmail();
            default:
                return null;
        }
    }

    public RegistrarTableRow getRow(int rowIndex) {
            return rows.get(rowIndex);
    }

}
