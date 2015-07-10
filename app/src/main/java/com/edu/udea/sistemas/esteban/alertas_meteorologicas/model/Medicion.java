package com.edu.udea.sistemas.esteban.alertas_meteorologicas.model;

/**
 * Created by esteban on 20/05/2015.
 */
public class Medicion {
    int id;
    int temperatura;
    int luz;
    int humedad;
    String fecha;


    public Medicion(int id, int temperatura, int luz, int humedad, String fecha) {
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

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getLuz() {
        return luz;
    }

    public void setLuz(int luz) {
        this.luz = luz;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
