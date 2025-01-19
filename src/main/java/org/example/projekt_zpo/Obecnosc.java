package org.example.projekt_zpo;

/**
 * Klasa reprezentująca obecność studenta na zajęciach.
 * Zawiera informacje o identyfikatorze obecności, studencie, terminie oraz statusie obecności.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Obecnosc {

    /**
     * Identyfikator obecności.
     */
    private int id;

    /**
     * Identyfikator studenta.
     */
    private int studentId;

    /**
     * Status obecności studenta.
     * 1 - nieobecny, 2 - obecny, 3 - spóźniony, 4 - usprawiedliwiony.
     */
    private int attendance;

    /**
     * Identyfikator terminu, do którego odnosi się obecność.
     */
    private int terminId;

    /**
     * Ustawia identyfikator studenta.
     *
     * @param studentId Identyfikator studenta do ustawienia.
     * @since 1.0
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    /**
     * Zwraca identyfikator studenta.
     *
     * @return Identyfikator studenta.
     * @since 1.0
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * Ustawia status obecności studenta.
     *
     * @param attendance Status obecności do ustawienia.
     * @since 1.0
     */
    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    /**
     * Zwraca status obecności studenta.
     *
     * @return Status obecności.
     * @since 1.0
     */
    public int getAttendance() {
        return attendance;
    }

    /**
     * Ustawia identyfikator terminu, do którego odnosi się obecność.
     *
     * @param terminId Identyfikator terminu do ustawienia.
     * @since 1.0
     */
    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    /**
     * Zwraca identyfikator terminu, do którego odnosi się obecność.
     *
     * @return Identyfikator terminu.
     * @since 1.0
     */
    public int getTerminId() {
        return terminId;
    }

    /**
     * Ustawia identyfikator obecności.
     *
     * @param id Identyfikator obecności do ustawienia.
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca identyfikator obecności.
     *
     * @return Identyfikator obecności.
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca status obecności studenta w postaci tekstowej.
     * Możliwe wartości:
     * - "nieobecny" dla statusu 1,
     * - "obecny" dla statusu 2,
     * - "spóźniony" dla statusu 3,
     * - "usprawiedliwiony" dla statusu 4,
     * - "brak" dla innych wartości.
     *
     * @return Status obecności w formie tekstowej.
     * @since 1.0
     */
    public String attendanceToString() {
        return switch (attendance) {
            case 1 -> "nieobecny";
            case 2 -> "obecny";
            case 3 -> "spóźniony";
            case 4 -> "usprawiedliwiony";
            default -> "brak";
        };
    }
}
