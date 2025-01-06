package org.example.projekt_zpo;

import java.sql.Date;

public class Termin {
    private int id;
    private int grupa_id;
    private String nazwa;
    private Date data;
    private int prowadzacy_id;

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setGrupa_id(int grupa_id) {
        this.grupa_id = grupa_id;
    }

    public int getGrupa_id() {
        return grupa_id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setProwadzacy_id(int prowadzacy_id) {
        this.prowadzacy_id = prowadzacy_id;
    }

    public int getProwadzacy_id() {
        return prowadzacy_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
