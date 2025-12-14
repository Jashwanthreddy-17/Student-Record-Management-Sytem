package org.example.studentapp;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public List<Student> findAll() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY id DESC";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public Student findById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    public void insert(Student s) throws SQLException {
        String sql = "INSERT INTO students (fullname, gender, dob, branch, phone, student_class, section, roll_number, grade) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            fillStatement(ps, s);
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) s.setId(keys.getInt(1));
            }
        }
    }

    public void update(Student s) throws SQLException {
        String sql = "UPDATE students SET fullname=?, gender=?, dob=?, branch=?, phone=?, student_class=?, section=?, roll_number=?, grade=? WHERE id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            fillStatement(ps, s);
            ps.setInt(10, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private void fillStatement(PreparedStatement ps, Student s) throws SQLException {
        ps.setString(1, s.getFullname());
        ps.setString(2, s.getGender());
        if (s.getDob() != null) ps.setDate(3, Date.valueOf(s.getDob())); else ps.setNull(3, Types.DATE);
        ps.setString(4, s.getBranch());
        ps.setString(5, s.getPhone());
        ps.setString(6, s.getStudentClass());
        ps.setString(7, s.getSection());
        ps.setString(8, s.getRollNumber());
        ps.setString(9, s.getGrade());
    }

    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setId(rs.getInt("id"));
        s.setFullname(rs.getString("fullname"));
        s.setGender(rs.getString("gender"));
        Date d = rs.getDate("dob");
        if (d != null) s.setDob(d.toLocalDate());
        s.setBranch(rs.getString("branch"));
        s.setPhone(rs.getString("phone"));
        s.setStudentClass(rs.getString("student_class"));
        s.setSection(rs.getString("section"));
        s.setRollNumber(rs.getString("roll_number"));
        s.setGrade(rs.getString("grade"));
        return s;
    }
}
