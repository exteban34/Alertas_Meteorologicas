package com.edu.udea.sistemas.esteban.alertas_meteorologicas.model;

import java.util.Date;

/**
 * Created by esteban on 28/04/2015.
 */
public class Alerta {
    private int temperatura;
    private int humedad;
    private int velocidad;
    private Date fecha;

    public Alerta(int temperatura, int humedad, int velocidad, Date fecha) {
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.velocidad = velocidad;
        this.fecha = fecha;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(int temperatura) {
        this.temperatura = temperatura;
    }

    public int getHumedad() {
        return humedad;
    }

    public void setHumedad(int humedad) {
        this.humedad = humedad;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
