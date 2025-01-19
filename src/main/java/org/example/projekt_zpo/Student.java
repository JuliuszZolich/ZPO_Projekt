package org.example.projekt_zpo;

/**
 * Klasa reprezentująca studenta.
 * Zawiera informacje o indeksie, imieniu, nazwisku oraz identyfikatorze grupy studenta.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Student {

    /**
     * Indeks studenta.
     */
    private int indeks;

    /**
     * Imię studenta.
     */
    private String imie;

    /**
     * Nazwisko studenta.
     */
    private String nazwisko;

    /**
     * Identyfikator grupy, do której należy student.
     */
    private int grupaId;

    /**
     * Ustawia indeks studenta.
     *
     * @param indeks Indeks studenta do ustawienia.
     * @since 1.0
     */
    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    /**
     * Ustawia imię studenta.
     *
     * @param imie Imię studenta do ustawienia.
     * @since 1.0
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Ustawia nazwisko studenta.
     *
     * @param nazwisko Nazwisko studenta do ustawienia.
     * @since 1.0
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Ustawia identyfikator grupy, do której należy student.
     *
     * @param grupaId Identyfikator grupy do ustawienia.
     * @since 1.0
     */
    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    /**
     * Zwraca indeks studenta.
     *
     * @return Indeks studenta.
     * @since 1.0
     */
    public int getIndeks() {
        return indeks;
    }

    /**
     * Zwraca imię studenta.
     *
     * @return Imię studenta.
     * @since 1.0
     */
    public String getImie() {
        return imie;
    }

    /**
     * Zwraca nazwisko studenta.
     *
     * @return Nazwisko studenta.
     * @since 1.0
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Zwraca identyfikator grupy, do której należy student.
     *
     * @return Identyfikator grupy.
     * @since 1.0
     */
    public int getGrupaId() {
        return grupaId;
    }

    /**
     * Zwraca indeks studenta w postaci tekstowej.
     *
     * @return Indeks studenta w formie tekstowej.
     * @since 1.0
     */
    public String getIndeksToString() {
        return String.valueOf(indeks);
    }
}
