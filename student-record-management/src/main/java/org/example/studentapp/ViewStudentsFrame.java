package org.example.studentapp;
//Inheritance
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ViewStudentsFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    private List<Student> allStudents = new ArrayList<>();

    // Pagination
    private int currentPage = 1;
    private int rowsPerPage = 10;
    private int totalPages = 1;

    private JLabel pageLabel;
    private JButton prevBtn, nextBtn;

    public ViewStudentsFrame() {
        setTitle("Student Records");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== TOP FILTER PANEL =====
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JLabel gradeLabel = new JLabel("Grade: ");
        JComboBox<String> gradeBox = new JComboBox<>(new String[]{"All", "A", "B", "C", "D", "F"});
        gradeBox.setPreferredSize(new Dimension(120, 28));

        JLabel nameLabel = new JLabel("Search Name: ");
        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 28));

        filterPanel.add(gradeLabel);
        filterPanel.add(gradeBox);
        filterPanel.add(nameLabel);
        filterPanel.add(nameField);

        add(filterPanel, BorderLayout.NORTH);

        // ===== TABLE MODEL WITH DELETE COLUMN =====
        String[] columns = {
                "ID", "Full Name", "Gender", "DOB",
                "Branch", "Phone", "Class", "Section",
                "Roll Number", "Grade", "Delete"
        };

        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 10; // Only the delete button column is clickable
            }
        };

        table = new JTable(model);
        //POLYMORPHISM
        // Add a button renderer for Delete Column
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), this));

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== PAGINATION PANEL =====
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        prevBtn = new JButton("◀ Previous");
        nextBtn = new JButton("Next ▶");
        pageLabel = new JLabel("Page 1/1");

        paginationPanel.add(prevBtn);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextBtn);

        add(paginationPanel, BorderLayout.SOUTH);

        // Load all students
        loadAllStudents();
        updatePage();

        // Filters
        gradeBox.addActionListener(e -> applyFilters(gradeBox, nameField));
        nameField.getDocument().addDocumentListener(new SimpleDocumentListener(() ->
                applyFilters(gradeBox, nameField)
        ));

        // Pagination
        prevBtn.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updatePage();
            }
        });

        nextBtn.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updatePage();
            }
        });
    }

    // ===== LOAD ALL STUDENTS FROM DB =====
    private void loadAllStudents() {
        try {
            StudentDAO dao = new StudentDAO();
            allStudents = dao.findAll();

            totalPages = (int) Math.ceil(allStudents.size() / (double) rowsPerPage);
            if (totalPages == 0) totalPages = 1;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error loading students: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== SHOW CURRENT PAGE =====
    public void updatePage() {
        model.setRowCount(0);

        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allStudents.size());

        for (int i = start; i < end; i++) {
            Student s = allStudents.get(i);
            model.addRow(new Object[]{
                    s.getId(), s.getFullname(), s.getGender(), s.getDob(),
                    s.getBranch(), s.getPhone(), s.getStudentClass(),
                    s.getSection(), s.getRollNumber(), s.getGrade(),
                    "Delete"
            });
        }

        pageLabel.setText("Page " + currentPage + "/" + totalPages);
    }

    // ===== FILTER (GRADE + NAME) =====
    private void applyFilters(JComboBox<String> gradeBox, JTextField nameField) {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        String grade = (String) gradeBox.getSelectedItem();
        String name = nameField.getText().trim();

        if (!grade.equals("All")) {
            filters.add(RowFilter.regexFilter("^" + grade + "$", 9));
        }

        if (!name.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + name, 1));
        }

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }

    // ===== DELETE STUDENT BY ID =====
    public void deleteStudent(int id) {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this student?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                StudentDAO dao = new StudentDAO();
                dao.delete(id);

                JOptionPane.showMessageDialog(this, "Student deleted!");

                loadAllStudents();
                updatePage();//Inheritance

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting student: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }//POLYMORPHISM
    }

    // =======================================================================
    // BUTTON RENDERER FOR TABLE
    // =======================================================================
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBackground(new Color(230, 57, 70));
            setForeground(Color.WHITE);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            setText("Delete");
            return this;
        }
    }

    // =======================================================================
    // BUTTON EDITOR FOR DELETE BUTTON
    // =======================================================================
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private int selectedRow;
        private ViewStudentsFrame parent;

        public ButtonEditor(JCheckBox checkBox, ViewStudentsFrame parent) {
            super(checkBox);
            this.parent = parent;

            button = new JButton("Delete");
            button.setOpaque(true);
            button.setBackground(new Color(220, 53, 69));
            button.setForeground(Color.WHITE);

            button.addActionListener((ActionEvent e) -> {
                int id = (int) table.getValueAt(selectedRow, 0);//inheritance
                parent.deleteStudent(id);
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            selectedRow = row;
            return button;
        }
    }//POLYMORPHISM
}
