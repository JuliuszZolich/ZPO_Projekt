package org.example.projekt_zpo;

public class Obecnosc {
    private int id;
    private int studentId;
    private int attendance;
    private int terminId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    public int getTerminId() {
        return terminId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String attendanceToString() {
        return switch (attendance) {
            case 1 -> "nieobecny";
            case 2 -> "obecny";
            case 3 -> "spoźniony";
            case 4 -> "usprawiedliwiony";
            default -> "brak";
        };
    }
}