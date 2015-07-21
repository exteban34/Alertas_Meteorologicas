package com.edu.udea.sistemas.esteban.alertas_meteorologicas.model;

import java.util.Date;

/**
 * Created by esteban on 28/04/2015.
 */
public class Alerta {
    private int id;
    private int temperaturaBajo;
    private int temperaturaAlto;
    private int humedadBajo;
    private int humedadAlto;
    private int luzBajo;
    private int luzAlto;
    private String fecha;
    private String label;
    private  int activa;



    public Alerta(int temperaturaBajo, int temperaturaAlto, int humedadBajo, int humedadAlto, int luzBajo, int luzAlto, String fecha) {
        this.temperaturaBajo = temperaturaBajo;
        this.temperaturaAlto = temperaturaAlto;
        this.humedadBajo = humedadBajo;
        this.humedadAlto = humedadAlto;
        this.luzBajo = luzBajo;
        this.luzAlto = luzAlto;
        this.fecha = fecha;

    }


    public Alerta(int temperaturaBajo, int temperaturaAlto, int humedadBajo, int humedadAlto, int luzBajo, int luzAlto, String fecha, String label, int activa) {
        this.temperaturaBajo = temperaturaBajo;
        this.temperaturaAlto = temperaturaAlto;
        this.humedadBajo = humedadBajo;
        this.humedadAlto = humedadAlto;
        this.luzBajo = luzBajo;
        this.luzAlto = luzAlto;
        this.fecha = fecha;
        this.label = label;
        this.activa = activa;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int isActiva() {
        return activa;
    }

    public void setActiva(int activa) {
        this.activa = activa;
    }

    public String getFecha() {
        return fecha;
    }

    public int getTemperaturaBajo() {
        return temperaturaBajo;
    }

    public int getTemperaturaAlto() {
        return temperaturaAlto;
    }

    public int getHumedadBajo() {
        return humedadBajo;
    }

    public int getHumedadAlto() {
        return humedadAlto;
    }

    public int getLuzBajo() {
        return luzBajo;
    }

    public int getLuzAlto() {
        return luzAlto;
    }

    public void setTemperaturaBajo(int temperaturaBajo) {
        this.temperaturaBajo = temperaturaBajo;
    }

    public void setTemperaturaAlto(int temperaturaAlto) {
        this.temperaturaAlto = temperaturaAlto;
    }

    public void setHumedadBajo(int humedadBajo) {
        this.humedadBajo = humedadBajo;
    }

    public void setHumedadAlto(int humedadAlto) {
        this.humedadAlto = humedadAlto;
    }

    public void setLuzBajo(int luzBajo) {
        this.luzBajo = luzBajo;
    }

    public void setLuzAlto(int luzAlto) {
        this.luzAlto = luzAlto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getActiva() {
        return activa;
    }
}
