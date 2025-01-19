package org.example.projekt_zpo;

/**
 * Klasa reprezentująca termin zajęć.
 * Zawiera informacje o identyfikatorze terminu, grupie, nazwie terminu, dacie oraz prowadzącym.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Termin {

    /**
     * Identyfikator terminu.
     */
    private int id;

    /**
     * Identyfikator grupy, do której należy termin.
     */
    private int grupaId;

    /**
     * Nazwa terminu.
     */
    private String nazwa;

    /**
     * Data terminu w formacie String.
     */
    private String data;

    /**
     * Identyfikator prowadzącego, który prowadzi ten termin.
     */
    private int prowadzacyId;

    /**
     * Ustawia nazwę terminu.
     *
     * @param nazwa Nazwa terminu do ustawienia.
     * @since 1.0
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Zwraca nazwę terminu.
     *
     * @return Nazwa terminu.
     * @since 1.0
     */
    public String getNazwa() {
        return nazwa;
    }

    /**
     * Ustawia identyfikator grupy, do której należy termin.
     *
     * @param grupaId Identyfikator grupy do ustawienia.
     * @since 1.0
     */
    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    /**
     * Zwraca identyfikator grupy, do której należy termin.
     *
     * @return Identyfikator grupy.
     * @since 1.0
     */
    public int getGrupaId() {
        return grupaId;
    }

    /**
     * Ustawia datę terminu.
     *
     * @param data Data terminu w formacie {@link String}.
     * @since 1.0
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Zwraca datę terminu.
     *
     * @return Data terminu w formacie {@link String}.
     * @since 1.0
     */
    public String getData() {
        return data;
    }

    /**
     * Ustawia identyfikator prowadzącego.
     *
     * @param prowadzacyId Identyfikator prowadzącego do ustawienia.
     * @since 1.0
     */
    public void setProwadzacyId(int prowadzacyId) {
        this.prowadzacyId = prowadzacyId;
    }

    /**
     * Zwraca identyfikator prowadzącego.
     *
     * @return Identyfikator prowadzącego.
     * @since 1.0
     */
    public int getProwadzacyId() {
        return prowadzacyId;
    }

    /**
     * Ustawia identyfikator terminu.
     *
     * @param id Identyfikator terminu do ustawienia.
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Zwraca identyfikator terminu.
     *
     * @return Identyfikator terminu.
     * @since 1.0
     */
    public int getId() {
        return id;
    }
}
