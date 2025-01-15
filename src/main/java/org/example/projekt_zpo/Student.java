package org.example.projekt_zpo;

import java.util.ArrayList;

public class Student {
    private int indeks;
    private String imie;
    private String nazwisko;
    private int grupaId;

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    public int getIndeks() {
        return indeks;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public int getGrupaId() {
        return grupaId;
    }

    public String getIndeksToString() {
        return String.valueOf(indeks);
    }
}
