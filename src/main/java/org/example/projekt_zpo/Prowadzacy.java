package org.example.projekt_zpo;

/**
 * Klasa reprezentująca prowadzącego zajęcia.
 * Zawiera informacje o identyfikatorze, imieniu, nazwisku oraz haśle prowadzącego.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Prowadzacy {

    /**
     * Identyfikator prowadzącego.
     */
    private int id;

    /**
     * Imię prowadzącego.
     */
    private String imie;

    /**
     * Nazwisko prowadzącego.
     */
    private String nazwisko;

    /**
     * Hasło prowadzącego.
     */
    private String haslo;

    /**
     * Ustawia identyfikator prowadzącego.
     *
     * @param id Identyfikator prowadzącego do ustawienia.
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Ustawia imię prowadzącego.
     *
     * @param imie Imię prowadzącego do ustawienia.
     * @since 1.0
     */
    public void setImie(String imie) {
        this.imie = imie;
    }

    /**
     * Ustawia nazwisko prowadzącego.
     *
     * @param nazwisko Nazwisko prowadzącego do ustawienia.
     * @since 1.0
     */
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    /**
     * Ustawia hasło prowadzącego.
     *
     * @param haslo Hasło prowadzącego do ustawienia.
     * @since 1.0
     */
    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    /**
     * Zwraca identyfikator prowadzącego.
     *
     * @return Identyfikator prowadzącego.
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca imię prowadzącego.
     *
     * @return Imię prowadzącego.
     * @since 1.0
     */
    public String getImie() {
        return imie;
    }

    /**
     * Zwraca nazwisko prowadzącego.
     *
     * @return Nazwisko prowadzącego.
     * @since 1.0
     */
    public String getNazwisko() {
        return nazwisko;
    }

    /**
     * Zwraca hasło prowadzącego.
     *
     * @return Hasło prowadzącego.
     * @since 1.0
     */
    public String getHaslo() {
        return haslo;
    }
}
