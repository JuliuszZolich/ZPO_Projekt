package org.example.projekt_zpo;

import java.sql.Date;

public class Termin {
    private int id;
    private int grupaId;
    private String nazwa;
    private String data;
    private int prowadzacyId;

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setGrupaId(int grupaId) {
        this.grupaId = grupaId;
    }

    public int getGrupaId() {
        return grupaId;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setProwadzacyId(int prowadzacyId) {
        this.prowadzacyId = prowadzacyId;
    }

    public int getProwadzacyId() {
        return prowadzacyId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
