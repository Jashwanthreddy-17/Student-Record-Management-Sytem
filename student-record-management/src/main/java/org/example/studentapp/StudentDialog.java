package org.example.studentapp;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StudentDialog extends JDialog {
    private final JTextField tfFullname = new JTextField(25);
    private final JComboBox<String> cbGender = new JComboBox<>(new String[]{"Male","Female","Other"});
    private final JTextField tfDob = new JTextField(10); // yyyy-mm-dd
    private final JTextField tfBranch = new JTextField(20);
    private final JTextField tfPhone = new JTextField(15);
    private final JTextField tfClass = new JTextField(10);
    private final JTextField tfSection = new JTextField(5);
    private final JTextField tfRoll = new JTextField(10);
    private final JComboBox<String> cbGrade = new JComboBox<>(new String[]{"A","B","C","D","E","F","-"});

    private final JButton btnSave = new JButton("Save");
    private final JButton btnCancel = new JButton("Cancel");

    private Student student;
    private boolean saved = false;

    public StudentDialog(Window owner) {
        super(owner, "Student", ModalityType.APPLICATION_MODAL);
        init();
    }

    private void init() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx=0; c.gridy=0; c.anchor=GridBagConstraints.EAST; c.insets = new Insets(4,4,4,4);
        p.add(new JLabel("Full name:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfFullname, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Gender:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(cbGender, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("DOB (yyyy-mm-dd):"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfDob, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Branch:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfBranch, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Phone:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfPhone, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Class:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfClass, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Section:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfSection, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Roll No:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(tfRoll, c);

        c.gridx=0; c.gridy++; c.anchor=GridBagConstraints.EAST; p.add(new JLabel("Grade:"), c); c.gridx=1; c.anchor=GridBagConstraints.WEST; p.add(cbGrade, c);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnSave);
        bottom.add(btnCancel);

        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> { saved = false; setVisible(false); });

        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
    }

    public void setStudent(Student s) {
        this.student = s;
        if (s == null) {
            tfFullname.setText("");
            cbGender.setSelectedIndex(0);
            tfDob.setText("");
            tfBranch.setText("");
            tfPhone.setText("");
            tfClass.setText("");
            tfSection.setText("");
            tfRoll.setText("");
            cbGrade.setSelectedItem("-");
        } else {
            tfFullname.setText(s.getFullname());
            cbGender.setSelectedItem(s.getGender());
            tfDob.setText(s.getDob() == null ? "" : s.getDob().toString());
            tfBranch.setText(s.getBranch());
            tfPhone.setText(s.getPhone());
            tfClass.setText(s.getStudentClass());
            tfSection.setText(s.getSection());
            tfRoll.setText(s.getRollNumber());
            cbGrade.setSelectedItem(s.getGrade() == null ? "-" : s.getGrade());
        }
    }

    private void onSave() {
        if (tfFullname.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Full name is required","Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (student == null) student = new Student();

        student.setFullname(tfFullname.getText().trim());
        student.setGender((String) cbGender.getSelectedItem());
        String dobText = tfDob.getText().trim();
        if (!dobText.isEmpty()) {
            try {
                LocalDate d = LocalDate.parse(dobText);
                student.setDob(d);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "DOB must be yyyy-mm-dd","Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }
        } else {
            student.setDob(null);
        }
        student.setBranch(tfBranch.getText().trim());
        student.setPhone(tfPhone.getText().trim());
        student.setStudentClass(tfClass.getText().trim());
        student.setSection(tfSection.getText().trim());
        student.setRollNumber(tfRoll.getText().trim());
        student.setGrade((String) cbGrade.getSelectedItem());

        saved = true;
        setVisible(false);
    }

    public boolean isSaved() { return saved; }
    public Student getStudent() { return student; }
}
