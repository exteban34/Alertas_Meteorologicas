package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.DataPass;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.Lista_Adaptador;



import java.util.ArrayList;



/**
 * Created by esteban on 09/07/2015.
 */
public class ListaMediciones extends Activity {



    private ListView lista;
    ArrayList<Medicion> mediciones = new ArrayList<>();
    Medicion medicion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_lista_mediciones);
        lista= (ListView) findViewById(R.id.listViewMediciones);
        DataPass dw = (DataPass) getIntent().getSerializableExtra("mediciones");
        mediciones = dw.getMediciones();


        lista.setAdapter(new Lista_Adaptador(this,R.layout.layaout_medicion_en_lista,mediciones) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    medicion = (Medicion) entrada;
                    TextView textViewFecha = (TextView) view.findViewById(R.id.textViewFechaMed);
                    if (textViewFecha != null){
                        textViewFecha.setText(medicion.getFecha());
                    }
                    TextView textViewTemp = (TextView) view.findViewById(R.id.textViewTempMed);
                    if (textViewTemp != null){
                        textViewTemp.setText(Double.toString(medicion.getTemperatura()));
                    }
                    TextView textViewHum = (TextView) view.findViewById(R.id.textViewHumMed);
                    if (textViewHum != null){
                        textViewHum.setText( Double.toString(medicion.getHumedad()));
                    }
                    TextView textViewLuz = (TextView) view.findViewById(R.id.textViewLuzMed);
                    if (textViewLuz != null){
                        textViewLuz.setText(Double.toString(medicion.getLuz()));
                    }
                    LinearLayout ly = (LinearLayout) view.findViewById(R.id.lyMedicion);
                    if(medicion.getId()%2==0){
                        ly.setBackgroundColor(getResources().getColor(R.color.antiquewhite));
                    }else{
                        ly.setBackgroundColor(getResources().getColor(R.color.gainsboro));
                    }
                }
            }
        });

    }


}
