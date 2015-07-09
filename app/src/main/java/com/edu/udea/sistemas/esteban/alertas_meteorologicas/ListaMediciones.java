package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.R;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.Lista_Adaptador;

import java.util.ArrayList;

/**
 * Created by esteban on 09/07/2015.
 */
public class ListaMediciones extends Activity {


    private ListView lista;
    private DBAdapter db = new DBAdapter(this);
    ArrayList<Alerta> alertas;
    Alerta alerta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_lista_alertas);
        lista= (ListView) findViewById(R.id.listViewAlertas);

        db.open();

        alertas=db.getTodasAlertas();

        db.close();
       lista.setAdapter(new Lista_Adaptador(this,R.layout.layaout_alerta_en_lista,alertas) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    alerta = (Alerta) entrada;

                    TextView tvtmp = (TextView) view.findViewById(R.id.tvTemp);
                    if (tvtmp != null){
                        tvtmp.setText(alerta.getTemperaturaBajo() + "  -  " + alerta.getTemperaturaAlto());
                    }
                    TextView tvhumd = (TextView) view.findViewById(R.id.tvHmd);
                    if (tvhumd != null){
                        tvhumd.setText(alerta.getHumedadBajo() + "  -  " + alerta.getHumedadAlto());
                    }
                    TextView tvluz = (TextView) view.findViewById(R.id.tvLuz);
                    if (tvluz != null){
                        tvluz.setText(alerta.getLuzBajo() + "  -  " + alerta.getLuzAlto());
                    }
                }
            }
        });
    }

}