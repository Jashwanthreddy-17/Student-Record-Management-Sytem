package org.example.studentapp;

import java.time.LocalDate;

public class Student {
    private int id;
    private String fullname;
    private String gender;
    private LocalDate dob;
    private String branch;
    private String phone;
    private String studentClass; // 'class' is reserved word, use studentClass
    private String section;
    private String rollNumber;
    private String grade;

    public Student() {}

    public Student(int id, String fullname, String gender, LocalDate dob, String branch,
                   String phone, String studentClass, String section, String rollNumber, String grade) {
        //----------------------OOP------------------------------------
        this.id = id;
        this.fullname = fullname;
        this.gender = gender;
        this.dob = dob;
        this.branch = branch;
        this.phone = phone;
        this.studentClass = studentClass;
        this.section = section;
        this.rollNumber = rollNumber;
        this.grade = grade;
       // -----------------------------------//
    }

    // getters and setters for all fields
    // ... generated below
//-------------------------ENCAPSULATION-----------------------
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
}
