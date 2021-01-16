package Models.Tables.Teacher;

import Models.DatabaseBehaviours.DBController;
import Models.Tables.Teacher.Row.GraduateTableRow;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GraduateTableModel extends AbstractTableModel {
    private final List<GraduateTableRow> graduates;

    private final String[] columnNames = new String[] {"Reg Number", "Achievement", "Qualification"};
    private final Class[] columnClass = new Class[] {int.class,String.class,String.class,};

    public GraduateTableModel(){
        this.graduates = this.getAllGraduates();
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
        return graduates.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        GraduateTableRow row = graduates.get(rowIndex);
        switch (columnIndex){
            case 0:
                return row.getRegNumber();
            case 1:
                return row.getAchievement();
            case 2:
                return row.getQualification();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex)
    {
    }


    public GraduateTableRow getRow(int rowIndex) {
        return graduates.get(rowIndex);
    }

    @Override
    public boolean isCellEditable(int rowindex, int columnIndex){
        return false;
    }

    private List<GraduateTableRow> getAllGraduates(){
        String query = "SELECT * FROM Graduates";
        System.out.println(query);
        List<GraduateTableRow> graduates = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(DBController.url,DBController.user,DBController.password)){
            Statement stmt = con.createStatement();
            ResultSet rs =  stmt.executeQuery(query);
            while(rs.next()){
                int regNumber = rs.getInt("regNumber");
                String achievement = rs.getString("Achievement");
                String qualification = rs.getString("Qualification");
                graduates.add(new GraduateTableRow(regNumber,achievement,qualification));
            }
            return graduates;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
