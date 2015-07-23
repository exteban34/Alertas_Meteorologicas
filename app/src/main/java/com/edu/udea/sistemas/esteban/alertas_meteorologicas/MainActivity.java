package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.DataPass;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.LeerJSON;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import servicios.MiServicio;


public class MainActivity extends ActionBarActivity {

    DBAdapter db= new DBAdapter(this);
    ProgressDialog pDialog;
    GraphView graphTemp,graphHum,graphLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //webViewTemperatura= (WebView) findViewById(R.id.webViewTemp);
        //webViewTemperatura.loadUrl("http://api.thingspeak.com/channels/44075/charts/1?width=450&height=260&results=60&dynamic=true");
        graphTemp = (GraphView) findViewById(R.id.temperatureGraph);
        graphHum = (GraphView) findViewById(R.id.humedadGraph);
        graphLuz = (GraphView) findViewById(R.id.luzGraph);
        new LeerMedicionesVista().execute("http://api.thingspeak.com/channels/44075/feed.json");
        Intent intent = new Intent(MainActivity.this, MiServicio.class);
        startService(intent);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id){

            case (R.id.registrar_menu_item):
                Intent intent_registrar=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.RegistroAlerta");
                startActivity(intent_registrar);
                break;
            case (R.id.ver_alertas_menu_item):
                Intent intent_ver_alertas=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
                startActivity(intent_ver_alertas);
                break;
            case (R.id.ver_mediciones_menu_item):
                new LeerMediciones().execute("http://api.thingspeak.com/channels/44075/feed.json");
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
    private class LeerMediciones extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            pDialog = new ProgressDialog(MainActivity.this);
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
                ArrayList<Medicion> mediciones= new ArrayList<>();
                Medicion medicion;
                pDialog.dismiss();
                JSONObject jsonObject= new JSONObject(result);
                JSONObject jsonObject1;
                JSONObject jsonchanel = jsonObject.getJSONObject("channel");
                int cantidad = jsonchanel.getInt("last_entry_id");
                JSONArray jsonfeeds = jsonObject.getJSONArray("feeds");

                for(int i =(jsonfeeds.length()-1) ; i >=(jsonfeeds.length()-5); i--){
                    jsonObject1=jsonfeeds.getJSONObject(i);
                    medicion = new Medicion(jsonObject1.getInt("entry_id"),
                            jsonObject1.getDouble("field1"),
                            jsonObject1.getDouble("field2"),
                            jsonObject1.getDouble("field3"),
                            jsonObject1.getString("created_at")
                    );

                    mediciones.add(medicion);


                }

                   Intent e = new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaMediciones");
                    e.putExtra("mediciones", new DataPass(mediciones));
                    startActivity(e);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class LeerMedicionesVista extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

        }

        protected String doInBackground(String... urls) {
            return LeerJSON.leerJSON(urls[0]);
        }

        protected void onPostExecute(String result) {
            try {
                /**
                 * Formatters para las labels de los grafos
                 */
                StaticLabelsFormatter staticLabelsFormatterTemp = new StaticLabelsFormatter(graphTemp);
                staticLabelsFormatterTemp.setHorizontalLabels(new String[] {"2 Horas","1 Hora", "Ahora"});
                StaticLabelsFormatter staticLabelsFormatterHum = new StaticLabelsFormatter(graphHum);
                staticLabelsFormatterHum.setHorizontalLabels(new String[] {"2 Horas", "1 Hora", "Ahora"});
                StaticLabelsFormatter staticLabelsFormatterLuz = new StaticLabelsFormatter(graphLuz);
                staticLabelsFormatterLuz.setHorizontalLabels(new String[] {"2 Horas", "1 Hora", "Ahora"});

                /**
                 * Arreglos de Datos para graficar

                 DataPoint[] datosTemp=new DataPoint[8];
                 DataPoint[] datosHum=new DataPoint[8];
                 DataPoint[] datosLuz=new DataPoint[8];
                */
                ArrayList<Medicion> mediciones= new ArrayList<>();
                Medicion medicion;
                JSONObject jsonObject= new JSONObject(result);
                JSONObject jsonObject1;
                JSONObject jsonchanel = jsonObject.getJSONObject("channel");
                JSONArray jsonfeeds = jsonObject.getJSONArray("feeds");
                int j=0;
                for(int i =(jsonfeeds.length()-11) ; i <jsonfeeds.length(); i++) {
                    jsonObject1=jsonfeeds.getJSONObject(i);
                    medicion = new Medicion(jsonObject1.getInt("entry_id"),
                            jsonObject1.getDouble("field1"),
                            jsonObject1.getDouble("field2"),
                            jsonObject1.getDouble("field3"),
                            jsonObject1.getString("created_at")
                    );
                    mediciones.add(medicion);
                }


                /**
                 * series para los graficos
                 */
                LineGraphSeries<DataPoint> serieTemp = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0,mediciones.get(0).getTemperatura()),
                        new DataPoint(1,mediciones.get(1).getTemperatura()),
                        new DataPoint(2,mediciones.get(2).getTemperatura()),
                        new DataPoint(3,mediciones.get(3).getTemperatura()),
                        new DataPoint(4,mediciones.get(4).getTemperatura()),
                        new DataPoint(5,mediciones.get(5).getTemperatura()),
                        new DataPoint(6,mediciones.get(6).getTemperatura()),
                        new DataPoint(7,mediciones.get(7).getTemperatura()),
                        new DataPoint(8,mediciones.get(8).getTemperatura()),
                        new DataPoint(9,mediciones.get(9).getTemperatura()),
                        new DataPoint(10,mediciones.get(10).getTemperatura())
                });
                LineGraphSeries<DataPoint> serieHum = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0,mediciones.get(0).getHumedad()),
                        new DataPoint(1,mediciones.get(1).getHumedad()),
                        new DataPoint(2,mediciones.get(2).getHumedad()),
                        new DataPoint(3,mediciones.get(3).getHumedad()),
                        new DataPoint(4,mediciones.get(4).getHumedad()),
                        new DataPoint(5,mediciones.get(5).getHumedad()),
                        new DataPoint(6,mediciones.get(6).getHumedad()),
                        new DataPoint(7,mediciones.get(7).getHumedad()),
                        new DataPoint(8,mediciones.get(8).getHumedad()),
                        new DataPoint(9,mediciones.get(9).getHumedad()),
                        new DataPoint(10,mediciones.get(10).getHumedad())
                });
                LineGraphSeries<DataPoint> serieLuz = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0,mediciones.get(0).getLuz()),
                        new DataPoint(1,mediciones.get(1).getLuz()),
                        new DataPoint(2,mediciones.get(2).getLuz()),
                        new DataPoint(3,mediciones.get(3).getLuz()),
                        new DataPoint(4,mediciones.get(4).getLuz()),
                        new DataPoint(5,mediciones.get(5).getLuz()),
                        new DataPoint(6,mediciones.get(6).getLuz()),
                        new DataPoint(7,mediciones.get(7).getLuz()),
                        new DataPoint(8,mediciones.get(8).getLuz()),
                        new DataPoint(9,mediciones.get(9).getLuz()),
                        new DataPoint(10,mediciones.get(10).getLuz())
                });

                /**
                 * Asigno las series correspondientes, doy formato  a los graficos
                  */

                graphTemp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatterTemp);
                serieTemp.setTitle("Temperatura");
                serieTemp.setColor(getResources().getColor(R.color.chocolate));
                graphTemp.getLegendRenderer().setVisible(true);
                graphTemp.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphTemp.setTitle("Temperatura");
                graphTemp.setTitleTextSize(28);
                graphTemp.setTitleColor(getResources().getColor(R.color.maroon));
                graphTemp.addSeries(serieTemp);


                graphHum.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatterHum);
                serieHum.setTitle("Humedad");
                serieHum.setColor(getResources().getColor(R.color.dodgerblue));
                graphHum.getLegendRenderer().setVisible(true);
                graphHum.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphHum.setTitle("Humedad Relativa");
                graphHum.setTitleTextSize(28);
                graphHum.setTitleColor(getResources().getColor(R.color.green));
                graphHum.addSeries(serieHum);

                graphLuz.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatterLuz);
                serieLuz.setTitle("Luz");
                serieLuz.setColor(getResources().getColor(R.color.darkgoldenrod));
                graphLuz.getLegendRenderer().setVisible(true);
                graphLuz.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphLuz.setTitle("Intensidad de la Luz");
                graphLuz.setTitleTextSize(28);
                graphLuz.setTitleColor(getResources().getColor(R.color.indigo));
                graphLuz.addSeries(serieLuz);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
