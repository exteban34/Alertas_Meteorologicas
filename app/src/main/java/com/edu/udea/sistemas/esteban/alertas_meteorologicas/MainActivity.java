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


public class MainActivity extends ActionBarActivity {

    DBAdapter db= new DBAdapter(this);
    ProgressDialog pDialog;
    ArrayList<Medicion> mediciones= new ArrayList<>();
    Medicion medicion;
    GraphView graphTemp,graphHum,graphLuz;
    //WebView webViewTemperatura;
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




    }

    public void clickRegistrarAlerta(View view){
        Intent intent=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.RegistroAlerta");
        startActivity(intent);
    }
    public void clickVerAlertas(View view){
        Intent intent=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaAlertas");
        startActivity(intent);
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


                JSONObject jsonObject= new JSONObject(result);
                JSONObject jsonObject1;
                JSONObject jsonchanel = jsonObject.getJSONObject("channel");
                JSONArray jsonfeeds = jsonObject.getJSONArray("feeds");
/**                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
              for(int i =(jsonfeeds.length()-1) ; i >=(jsonfeeds.length()-5); i--){
                    jsonObject1=jsonfeeds.getJSONObject(i);
                    medicion = new Medicion(jsonObject1.getInt("entry_id"),
                            jsonObject1.getDouble("field1"),
                            jsonObject1.getDouble("field2"),
                            jsonObject1.getDouble("field3"),
                            jsonObject1.getString("created_at")
                    );

                }
             Date date1 = formatter.parse("2015-06-25T21:39:37Z");
                Date date2 = formatter.parse("2015-06-26T21:39:37Z");
                Date date3 = formatter.parse("2015-06-30T21:39:37Z");
                Date date4 = formatter.parse("2015-06-25T22:39:37Z");
 */
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0,35.3),
                        new DataPoint(1,30.5),
                        new DataPoint(2,32.2),
                        new DataPoint(3,33.4),
                        new DataPoint(4,34)
                });
                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                        new DataPoint(0, 3),
                        new DataPoint(1, 6),
                        new DataPoint(2, 1),
                        new DataPoint(3, 2),
                        new DataPoint(4, 5)
                });
                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graphTemp);
                staticLabelsFormatter.setHorizontalLabels(new String[] {"old", "middle", "new"});
                graphTemp.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                series.setTitle("ALgo");
                series2.setTitle("Otro");
                series.setColor(getResources().getColor(R.color.peru));
                graphTemp.getLegendRenderer().setVisible(true);
                graphTemp.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graphTemp.setTitle("Temperatura");
                graphTemp.setTitleTextSize(24);
                graphTemp.setTitleColor(getResources().getColor(R.color.maroon));
                graphTemp.addSeries(series);
                graphHum.addSeries(series2);




            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
