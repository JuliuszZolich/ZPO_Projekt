package org.example.projekt_zpo;

public class StudentListaObecnosci {
    Student student;
    Obecnosc attendance;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setAttendance(Obecnosc attendance) {
        this.attendance = attendance;
    }

    public String getAttendanceToString() {
        return attendance.attendanceToString();
    }

    public String getStudentNameToString() {
        return student.getImie();
    }

    public String getStudentSurnameToString() {
        return student.getNazwisko();
    }

    public String getStudentIndexToString() {
        return student.getIndeksToString();
    }
}
