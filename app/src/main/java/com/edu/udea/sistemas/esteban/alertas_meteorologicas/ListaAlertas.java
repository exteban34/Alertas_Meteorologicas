package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.Lista_Adaptador;

import java.util.ArrayList;

/**
 * Created by esteban on 28/04/2015.
 */
public class ListaAlertas extends Activity {


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
       lista.setAdapter(new Lista_Adaptador(this, R.layout.layaout_alerta_en_lista, alertas) {
           @Override
           public void onEntrada(Object entrada, View view) {
               if (entrada != null) {
                   alerta = (Alerta) entrada;
                   TextView tvlbl = (TextView) view.findViewById(R.id.tvId);
                   if (tvlbl != null) {
                       tvlbl.setText(alerta.getLabel());
                   }

                   TextView tvtmp = (TextView) view.findViewById(R.id.tvTemp);
                   if (tvtmp != null) {
                       tvtmp.setText(alerta.getTemperaturaBajo() + "  -  " + alerta.getTemperaturaAlto());
                   }
                   TextView tvhumd = (TextView) view.findViewById(R.id.tvHmd);
                   if (tvhumd != null) {
                       tvhumd.setText(alerta.getHumedadBajo() + "  -  " + alerta.getHumedadAlto());
                   }
                   TextView tvluz = (TextView) view.findViewById(R.id.tvLuz);
                   if (tvluz != null) {
                       tvluz.setText(alerta.getLuzBajo() + "  -  " + alerta.getLuzAlto());
                   }

                   LinearLayout ly = (LinearLayout) view.findViewById(R.id.lyAlerta);
                   if (alerta.getActiva() == 1) {
                       ly.setBackgroundColor(getResources().getColor(R.color.honeydew));
                   } else {
                       ly.setBackgroundColor(getResources().getColor(R.color.mistyrose));
                   }
               }
           }
       });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view,
                                    int posicion, long id) {

                alerta = (Alerta) pariente
                        .getItemAtPosition(posicion);
                int alertaID = alerta.getId();
                Intent i = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.EditarAlerta");
                i.putExtra("idAlerta", alertaID);
                startActivity(i);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.MainActivity");
        startActivity(i);
    }
}
