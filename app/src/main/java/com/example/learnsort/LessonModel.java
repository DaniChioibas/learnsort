package com.example.learnsort;

public class LessonModel {
    private String id;
    private String informatii;
    private String codSursa;
    private String nume;

    public LessonModel(String id, String informatii, String codSursa, String nume) {
        this.id = id;
        this.informatii = informatii;
        this.codSursa = codSursa;
        this.nume = nume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformatii() {
        return informatii;
    }

    public void setInformatii(String informatii) {
        this.informatii = informatii;
    }

    public String getCodSursa() {
        return codSursa;
    }

    public void setCodSursa(String codSursa) {
        this.codSursa = codSursa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
