package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.model.Medicion;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.DataPass;
import com.edu.udea.sistemas.esteban.alertas_meteorologicas.util.LeerJSON;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    DBAdapter db= new DBAdapter(this);
    ProgressDialog pDialog;
    ArrayList<Medicion> mediciones= new ArrayList<>();
    Medicion medicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                for(int i =(cantidad-1) ; i >=(cantidad-5); i--){
                    jsonObject1=jsonfeeds.getJSONObject(i);
                    medicion = new Medicion(jsonObject1.getInt("entry_id"),
                            jsonObject1.getInt("field1"),
                            jsonObject1.getInt("field2"),
                            jsonObject1.getInt("field3"),
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
}
