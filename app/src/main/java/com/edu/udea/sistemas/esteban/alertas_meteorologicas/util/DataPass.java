package com.edu.udea.sistemas.esteban.alertas_meteorologicas.util;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Created by esteban on 10/07/2015.
 */

    public class DataPass implements Serializable {

        private ArrayList<Medicion> mediciones;

        public DataPass(ArrayList<Medicion> data) {
            this.mediciones = data;
        }

        public ArrayList<Medicion> getMediciones() {
            return this.mediciones;
        }
}
