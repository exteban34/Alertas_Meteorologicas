package com.edu.udea.sistemas.esteban.alertas_meteorologicas.model;

import java.io.Serializable;

/**
 * Created by esteban on 20/05/2015.
 */
public class Medicion implements Serializable {
    int id;
    double temperatura;
    double luz;
    double humedad;
    String fecha;


    public Medicion(int id, double temperatura,  double humedad,double luz, String fecha) {
        this.id = id;
        this.temperatura = temperatura;
        this.luz = luz;
        this.humedad = humedad;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    public double getLuz() {
        return luz;
    }

    public void setLuz(double luz) {
        this.luz = luz;
    }

    public double getHumedad() {
        return humedad;
    }

    public void setHumedad(double humedad) {
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
