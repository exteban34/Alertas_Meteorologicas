package com.edu.udea.sistemas.esteban.alertas_meteorologicas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.edu.udea.sistemas.esteban.alertas_meteorologicas.db.DBAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends ActionBarActivity {

    DBAdapter db= new DBAdapter(this);
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
                Intent intent_ver_mediciones=new Intent("com.edu.udea.sistemas.esteban.alertas_meteorologicas.ListaMediciones");
                startActivity(intent_ver_mediciones);
                break;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
