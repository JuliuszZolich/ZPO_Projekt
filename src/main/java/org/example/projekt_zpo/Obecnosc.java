package org.example.projekt_zpo;

public class Obecnosc {
    private int id;
    private int student_id;
    private int attendance;
    private int termin_id;

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setTermin_id(int termin_id) {
        this.termin_id = termin_id;
    }

    public int getTermin_id() {
        return termin_id;
    }
}