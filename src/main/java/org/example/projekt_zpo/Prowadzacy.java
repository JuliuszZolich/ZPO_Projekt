package org.example.projekt_zpo;

public class Prowadzacy {
    private int id;
    private String imie;
    private String nazwisko;
    private String haslo;

    public void SetId(int id) {
        this.id = id;
    }

    public void SetImie(String imie) {
        this.imie = imie;
    }

    public void SetNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public void SetHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int GetId() {
        return id;
    }

    public String GetImie() {
        return imie;
    }

    public String GetNazwisko() {
        return nazwisko;
    }

    public String GetHaslo() {
        return haslo;
    }
}