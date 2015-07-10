package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.R;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Alerta;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.LeerJSON;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.Lista_Adaptador;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by esteban on 09/07/2015.
 */
public class ListaMediciones extends Activity {


    ProgressDialog pDialog;
    private ListView lista;
    private DBAdapter db = new DBAdapter(this);
    ArrayList<Medicion> mediciones;
    Medicion medicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layaout_lista_mediciones);
        lista= (ListView) findViewById(R.id.listViewMediciones);
        new LeerMediciones().execute("http://api.thingspeak.com/channels/44075/feed.json");
    }
    private class LeerMediciones extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            pDialog = new ProgressDialog(ListaMediciones.this);
            pDialog.setMessage(getString(R.string.carga_datos));
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... urls) {
            return LeerJSON.leerJSON(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                pDialog.dismiss();
                /**
                 * Obtengo listado de tareas del auxiliar
                 */
                JSONObject jsonObject= new JSONObject(result);
                JSONObject jsonObject1;
                JSONObject jsonchanel = jsonObject.getJSONObject("channel");
                JSONArray jsonfeeds = jsonObject.getJSONArray("feeds");

                for(int i = 0; i <= jsonfeeds.length(); i++){
                   jsonObject1=jsonfeeds.getJSONObject(i);
                    Toast.makeText(
                            getApplicationContext(),"jsonmedicion"+jsonObject1.getString("created_at"),
                            Toast.LENGTH_SHORT).show();
                    medicion = new Medicion(jsonObject1.getInt("entry_id"),
                            jsonObject1.getInt("field1"),
                            jsonObject1.getInt("field2"),
                            jsonObject1.getInt("field3"),
                            jsonObject1.getString("created_at")
                    );

                //mediciones.add(medicion);
                    Toast.makeText(
                            getApplicationContext(),"Medicion"+medicion.getFecha()+"  "
                                    +medicion.getTemperatura()+"  "+medicion.getHumedad()+
                                     "  "+medicion.getLuz(),
                            Toast.LENGTH_LONG).show();

                }

                lista.setAdapter(new Lista_Adaptador(ListaMediciones.this,R.layout.layaout_medicion_en_lista,mediciones) {
                    @Override
                    public void onEntrada(Object entrada, View view) {
                        if (entrada != null) {
                            medicion = (Medicion) entrada;
                            TextView tvtfecha = (TextView) view.findViewById(R.id.tvfechaMed);
                            if (tvtfecha != null){
                                tvtfecha.setText(medicion.getFecha());
                            }
                            TextView tvtmp = (TextView) view.findViewById(R.id.tvTempMed);
                            if (tvtmp != null){
                                tvtmp.setText(medicion.getTemperatura());
                            }
                            TextView tvhumd = (TextView) view.findViewById(R.id.tvHmdMed);
                            if (tvhumd != null){
                                tvhumd.setText(medicion.getHumedad());
                            }
                            TextView tvluz = (TextView) view.findViewById(R.id.tvLuzMed);
                            if (tvluz != null){
                                tvluz.setText(medicion.getLuz());
                            }
                        }
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
