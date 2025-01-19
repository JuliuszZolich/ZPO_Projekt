package org.example.projekt_zpo;

/**
 * Klasa reprezentująca powiązanie studenta z jego obecnością na zajęciach.
 * Zawiera informacje o studencie oraz jego statusie obecności.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class StudentListaObecnosci {

    /**
     * Obiekt reprezentujący studenta.
     * Jest to instancja klasy {@link Student}, która zawiera dane o studencie.
     *
     * @see Student
     */
    Student student;

    /**
     * Obiekt reprezentujący obecność studenta.
     * Jest to instancja klasy {@link Obecnosc}, która zawiera informacje o statusie obecności studenta.
     *
     * @see Obecnosc
     */
    Obecnosc attendance;

    /**
     * Zwraca obiekt studenta.
     *
     * @return Obiekt studenta.
     * @since 1.0
     * @see Student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Ustawia obiekt studenta.
     *
     * @param student Obiekt studenta do ustawienia.
     * @since 1.0
     * @see Student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Ustawia obiekt obecności studenta.
     *
     * @param attendance Obiekt obecności do ustawienia.
     * @since 1.0
     * @see Obecnosc
     */
    public void setAttendance(Obecnosc attendance) {
        this.attendance = attendance;
    }

    /**
     * Zwraca status obecności studenta w postaci tekstowej.
     * Jeśli obecność jest nieokreślona, zwraca "brak".
     *
     * @return Status obecności w formie tekstowej.
     * @since 1.0
     * @see Obecnosc#attendanceToString()
     */
    public String getAttendanceToString() {
        String attendanceString = "brak";
        try {
            attendanceString = attendance.attendanceToString();
        }
        catch (Exception ignored) {}
        return attendanceString;
    }

    /**
     * Zwraca imię studenta w postaci tekstowej.
     *
     * @return Imię studenta.
     * @since 1.0
     * @see Student#getImie()
     */
    public String getStudentNameToString() {
        return student.getImie();
    }

    /**
     * Zwraca nazwisko studenta w postaci tekstowej.
     *
     * @return Nazwisko studenta.
     * @since 1.0
     * @see Student#getNazwisko()
     */
    public String getStudentSurnameToString() {
        return student.getNazwisko();
    }

    /**
     * Zwraca indeks studenta w postaci tekstowej.
     *
     * @return Indeks studenta.
     * @since 1.0
     * @see Student#getIndeksToString()
     */
    public String getStudentIndexToString() {
        return student.getIndeksToString();
    }
}
