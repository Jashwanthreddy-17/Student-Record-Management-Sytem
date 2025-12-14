package org.example.studentapp;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {
    private final String[] columns = {"ID","Full name","Gender","DOB","Branch","Phone","Class","Section","Roll No","Grade"};
    private List<Student> students = new ArrayList<>();
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void setStudents(List<Student> students) {
        this.students = students;
        fireTableDataChanged();
    }

    public Student getStudentAt(int row) {
        if (row < 0 || row >= students.size()) return null;
        return students.get(row);
    }
//----------------------POYlMORPHISIM------------------------------------//
    @Override
    public int getRowCount() { return students.size(); }

    @Override
    public int getColumnCount() { return columns.length; }

    @Override
    public String getColumnName(int column) { return columns[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        //--------------------------------------------------------------------------//
        //-----------------SWITCH CASE---------------------------------------------//
        return switch (columnIndex) {
            case 0 -> s.getId();
            case 1 -> s.getFullname();
            case 2 -> s.getGender();
            case 3 -> s.getDob() == null ? "" : s.getDob().format(fmt);
            case 4 -> s.getBranch();
            case 5 -> s.getPhone();
            case 6 -> s.getStudentClass();
            case 7 -> s.getSection();
            case 8 -> s.getRollNumber();
            case 9 -> s.getGrade();
            default -> null;
            //-------------------------------------------------------------------//
        };
    }
}
