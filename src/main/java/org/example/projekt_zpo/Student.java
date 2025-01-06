package org.example.projekt_zpo;

public class Student {
    private int id;
    private int indeks;
    private String imie;
    private String nazwisko;
    private int grupa_id;
    private String haslo;

    public void setId(int id) {
        this.id = id;
    }

    public void setIndeks(int indeks) {
        this.indeks = indeks;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setGrupa_id(int grupa_id) {
        this.grupa_id = grupa_id;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getId() {
        return id;
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

    public int getGrupa_id() {
        return grupa_id;
    }

    public String getHaslo() {
        return haslo;
    }
}
