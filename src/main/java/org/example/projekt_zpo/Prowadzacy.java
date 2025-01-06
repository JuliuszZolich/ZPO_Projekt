package org.example.projekt_zpo;

public class Prowadzacy {
    private int id;
    private String imie;
    private String nazwisko;
    private String haslo;

    public void setId(int id) {
        this.id = id;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int getId() {
        return id;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public String getHaslo() {
        return haslo;
    }
}