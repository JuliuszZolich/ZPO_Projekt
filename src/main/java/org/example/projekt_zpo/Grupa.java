package org.example.projekt_zpo;

import java.util.ArrayList;

/**
 * Klasa reprezentująca grupę w aplikacji.
 * Zawiera dane dotyczące identyfikatora grupy oraz jej nazwy.
 *
 * @author Sebastian Cieślik
 * @version 1.0
 */
public class Grupa {

    /**
     * Identyfikator grupy.
     */
    int id;

    /**
     * Nazwa grupy.
     */
    String nazwa;

    /**
     * Ustawia identyfikator grupy.
     *
     * @param id Identyfikator grupy do ustawienia.
     * @since 1.0
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Ustawia nazwę grupy.
     *
     * @param nazwa Nazwa grupy do ustawienia.
     * @since 1.0
     */
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    /**
     * Zwraca identyfikator grupy.
     *
     * @return Identyfikator grupy.
     * @since 1.0
     */
    public int getId() {
        return id;
    }

    /**
     * Zwraca nazwę grupy.
     *
     * @return Nazwa grupy.
     * @since 1.0
     */
    public String getNazwa() {
        return nazwa;
    }
}
